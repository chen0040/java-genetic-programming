package com.github.chen0040.gp.treegp.gp;


import com.github.chen0040.data.utils.TupleTwo;
import com.github.chen0040.gp.commons.TournamentSelection;
import com.github.chen0040.gp.commons.TournamentSelectionResult;
import com.github.chen0040.gp.lgp.program.Program;
import com.github.chen0040.gp.services.RandEngine;
import com.github.chen0040.gp.treegp.TreeGP;
import com.github.chen0040.gp.treegp.enums.TGPPopulationReplacementStrategy;
import com.github.chen0040.gp.treegp.program.Solution;
import com.github.chen0040.gp.utils.CollectionUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Created by xschen on 15/5/2017.
 */
@Getter
@Setter
public class Population {

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

   protected void evaluate(TreeGP manager) {

      double bestCost = Double.MAX_VALUE;
      for(int i=0; i< solutions.size(); ++i) {
         Solution p = solutions.get(i);
         double cost = manager.evaluateCost(p);
         p.setCost(cost);
         p.setCostValid(true);
         if (p.getCost() < bestCost) {
            bestSolutionInCurrentGeneration = p;
            bestCost = p.getCost();
         }
      }

      updateGlobal(bestSolutionInCurrentGeneration);
   }

   private void updateGlobal(Solution solution) {
      if(!globalBestSolution.isPresent() || CollectionUtils.isBetterThan(solution, globalBestSolution.get()))
      {
         globalBestSolution = Optional.of(solution.makeCopy());
      }
   }

   public void initialize(){
      PopulationInitialization.initialize(solutions, manager);
      evaluate(manager);
   }

   public void evolve(){

      TGPPopulationReplacementStrategy populationReplacement = manager.getPopulationReplacementStrategy();
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

      int elite_count=(int)(manager.getElitismRatio() * iPopSize);

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

         Mutation.apply(child, manager);

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

         double cost = manager.evaluateCost(child);
         child.setCost(cost);
         child.setCostValid(true);

         if (bestSolutionInCurrentGeneration == null || child.isBetterThan(bestSolutionInCurrentGeneration))
         {
            bestSolutionInCurrentGeneration = child;
         }
      }


      updateGlobal(bestSolutionInCurrentGeneration);

      solutions.sort(Solution::compareTo);
      offspring.sort(Solution::compareTo);

      for (int offspring_index = elite_count; offspring_index < iPopSize; ++offspring_index)
      {
         solutions.set(offspring_index, offspring.get(offspring_index-elite_count));
      }
   }

   private void tinyGPEvolve(){

   }

}
