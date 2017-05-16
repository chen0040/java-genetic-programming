package com.github.chen0040.gp.treegp.gp;


import com.github.chen0040.gp.treegp.TreeGP;
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
   private int currentGenertion;

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

}
