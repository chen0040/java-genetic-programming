package com.github.chen0040.gp.treegp.gp;


import com.github.chen0040.data.utils.TupleTwo;
import com.github.chen0040.gp.commons.TournamentSelection;
import com.github.chen0040.gp.commons.TournamentSelectionResult;
import com.github.chen0040.gp.services.RandEngine;
import com.github.chen0040.gp.treegp.TreeGP;
import com.github.chen0040.gp.treegp.enums.TGPPopulationReplacementStrategy;
import com.github.chen0040.gp.treegp.program.Solution;
import com.github.chen0040.gp.utils.CollectionUtils;
import com.github.chen0040.gp.utils.QuickSort;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Created by xschen on 15/5/2017.
 */
@Getter
@Setter
public class Population {

   private static final Logger logger = LoggerFactory.getLogger(Population.class);

   private final List<Solution> solutions = new ArrayList<>();
   private final TreeGP manager;

   @Setter(AccessLevel.NONE)
   @Getter(AccessLevel.NONE)
   private Optional<Solution> globalBestSolution = Optional.empty();

   private Solution bestSolutionInCurrentGeneration = null;
   private int currentGeneration;

   public Population(TreeGP manager) {
      this.manager = manager;
   }

   protected void evaluate() {

      double bestCost = Double.MAX_VALUE;
      for(int i=0; i< solutions.size(); ++i) {
         Solution p = solutions.get(i);
         evaluate(p);
         if (p.getCost() < bestCost) {
            bestSolutionInCurrentGeneration = p;
            bestCost = p.getCost();
         }
      }

      updateGlobal(bestSolutionInCurrentGeneration);
   }

   private void evaluate(Solution solution) {
      if(solution.isCostValid()) return;

      double cost = manager.evaluateCost(solution);
      solution.setCost(cost);
      solution.setCostValid(true);
   }

   private void updateGlobal(Solution solution) {
      if(!globalBestSolution.isPresent() || CollectionUtils.isBetterThan(solution, globalBestSolution.get()))
      {
         globalBestSolution = Optional.of(solution.makeCopy());
      }
   }

   public void initialize(){
      PopulationInitialization.apply(solutions, manager);
      evaluate();
   }

   public boolean isTerminated() {
      return currentGeneration >= manager.getMaxGeneration();
   }

   public void evolve(){

      TGPPopulationReplacementStrategy populationReplacement = manager.getReplacementStrategy();
      if (populationReplacement == TGPPopulationReplacementStrategy.MuPlusLambda)
      {
         muPlusLambdaEvolve();
      }
      else if (populationReplacement == TGPPopulationReplacementStrategy.TinyGP)
      {
         tinyGPEvolve();
      }

      currentGeneration++;
   }

   private void muPlusLambdaEvolve() {
      RandEngine randEngine = manager.getRandEngine();
      int iPopSize = this.manager.getPopulationSize();

      int elite_count = (int)(manager.getElitismRatio() * iPopSize);

      int crossover_count = (int)(this.manager.getCrossoverRate() * iPopSize);

      if (crossover_count % 2 != 0) crossover_count += 1;

      int micro_mutation_count = (int)(this.manager.getMicroMutationRate() * iPopSize);
      int macro_mutation_count = (int)(this.manager.getMacroMutationRate() * iPopSize);
      int reproduction_count = iPopSize - crossover_count - micro_mutation_count - macro_mutation_count;



      List<Solution> offspring = new ArrayList<>();

      bestSolutionInCurrentGeneration = null;

      //do crossover
      for (int offspring_index = 0; offspring_index < crossover_count; offspring_index += 2)
      {
         TournamentSelectionResult<Solution> tournament = TournamentSelection.select(solutions, randEngine);
         TupleTwo<Solution, Solution> tournament_winners = tournament.getWinners();


         Solution child1 = tournament_winners._1().makeCopy();
         Solution child2 = tournament_winners._2().makeCopy();

         Crossover.apply(child1, child2, manager);

         offspring.add(child1);
         offspring.add(child2);
      }

      // do point mutation
      for (int offspring_index = 0; offspring_index < micro_mutation_count; ++offspring_index)
      {

         TournamentSelectionResult<Solution> tournament = TournamentSelection.select(solutions, randEngine);
         TupleTwo<Solution, Solution> tournament_winners = tournament.getWinners();

         Solution child = tournament_winners._1().makeCopy();

         MicroMutation.apply(child, manager);

         offspring.add(child);
      }

      // do subtree mutation
      for (int offspring_index = 0; offspring_index < macro_mutation_count; ++offspring_index)
      {
         TournamentSelectionResult<Solution> tournament = TournamentSelection.select(solutions, randEngine);
         TupleTwo<Solution, Solution> tournament_winners = tournament.getWinners();

         Solution child = tournament_winners._1().makeCopy();

         MacroMutation.apply(child, manager);

         offspring.add(child);

      }

      // do reproduction
      for (int offspring_index = 0; offspring_index < reproduction_count; ++offspring_index)
      {
         TournamentSelectionResult<Solution> tournament = TournamentSelection.select(solutions, randEngine);
         TupleTwo<Solution, Solution> tournament_winners = tournament.getWinners();
         Solution child = tournament_winners._1().makeCopy();

         offspring.add(child);
      }

      for (int i = 0; i < iPopSize; ++i)
      {
         Solution child = offspring.get(i);

         evaluate(child);

         if (bestSolutionInCurrentGeneration == null || child.isBetterThan(bestSolutionInCurrentGeneration))
         {
            bestSolutionInCurrentGeneration = child;
         }
      }


      updateGlobal(bestSolutionInCurrentGeneration);

      QuickSort.sort(solutions, Solution::compareTo);
      QuickSort.sort(offspring, Solution::compareTo);

      for (int offspring_index = elite_count; offspring_index < iPopSize; ++offspring_index)
      {
         solutions.set(offspring_index, offspring.get(offspring_index-elite_count));
      }
   }

   /// <summary>
   /// Similar to Evolve2, but use offspring solution to replace bad parent, in a way similar to TinyGP as as specified in "A Field Guide to Genetic Programming"
   /// </summary>
   /// <param name="report_handler"></param>
   private void tinyGPEvolve()
   {
      int iPopSize = this.manager.getPopulationSize();

      double sum_rate = manager.getCrossoverRate()+manager.getMacroMutationRate()+manager.getMicroMutationRate()+manager.getReproductionRate();
      double crossover_disk = manager.getCrossoverRate() / sum_rate;
      double micro_mutation_disk = (manager.getCrossoverRate() + manager.getMicroMutationRate()) / sum_rate;
      double macro_mutation_disk = (manager.getCrossoverRate() + manager.getMicroMutationRate() + manager.getMacroMutationRate()) / sum_rate;

      RandEngine randEngine = manager.getRandEngine();

      bestSolutionInCurrentGeneration = globalBestSolution.orElse(null);
      for (int offspring_index = 0; offspring_index < iPopSize; offspring_index += 1)
      {
         double r = randEngine.uniform();
         List<Solution> children = new ArrayList<>();

         List<Solution> bad_parents = new ArrayList<>();

         TournamentSelectionResult<Solution> tournament = TournamentSelection.select(solutions, randEngine);
         TupleTwo<Solution, Solution> tournament_winners = tournament.getWinners();
         TupleTwo<Solution, Solution> tournament_losers = tournament.getLosers();

         bad_parents.add(tournament_losers._1());
         bad_parents.add(tournament_losers._2());

         if (r <= crossover_disk) // do crossover
         {
            Solution child1 = tournament_winners._1().makeCopy();
            Solution child2 = tournament_winners._2().makeCopy();

            Crossover.apply(child1, child2, manager);

            children.add(child1);
            children.add(child2);
         }
         else if (r <= micro_mutation_disk) // do point mutation
         {
            Solution child = tournament_winners._1().makeCopy();
            MicroMutation.apply(child, manager);
            children.add(child);
         }
         else if (r <= macro_mutation_disk) // do subtree mutation
         {
            Solution child = tournament_winners._1().makeCopy();
            MacroMutation.apply(child, manager);
            children.add(child);
         }
         else // do reproduction
         {
            Solution child = tournament_winners._1().makeCopy();
            children.add(child);
         }


         boolean successfully_replaced = false;
         for(int i=0; i < children.size(); ++i)
         {
            Solution child = children.get(i);

            evaluate(child);

            if (bestSolutionInCurrentGeneration == null || child.isBetterThan(bestSolutionInCurrentGeneration))
            {
               bestSolutionInCurrentGeneration = child;
            }

            for (Solution bad_parent : bad_parents)
            {
               if (child.isBetterThan(bad_parent))
               {
                  successfully_replaced = true;
                  solutions.set(solutions.indexOf(bad_parent), child);
                  break;
               }
            }

            if (successfully_replaced)
            {
               break;
            }
         }

      }

      updateGlobal(bestSolutionInCurrentGeneration);
   }


   public Solution getGlobalBestSolution() {
      return globalBestSolution.get();
   }


   public double getCostInCurrentGeneration() {
      return bestSolutionInCurrentGeneration.getCost();
   }


   public int size() {
      return solutions.size();
   }
}
