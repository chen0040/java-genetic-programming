package com.github.chen0040.gp.treegp.gp;


import com.github.chen0040.gp.treegp.TreeGP;
import com.github.chen0040.gp.treegp.enums.TGPInitializationStrategy;
import com.github.chen0040.gp.treegp.program.*;

import java.util.List;


/**
 * Created by xschen on 16/5/2017.
 */
public class PopulationInitialization {

   public static void initialize(List<Solution> solutions, TreeGP programManager){
      TGPInitializationStrategy initializationStrategy = programManager.getInitializationStrategy();
      if(initializationStrategy == TGPInitializationStrategy.INITIALIZATION_METHOD_FULL
              || initializationStrategy == TGPInitializationStrategy.INITIALIZATION_METHOD_GROW
              || initializationStrategy == TGPInitializationStrategy.INITIALIZATION_METHOD_PTC1
              || initializationStrategy == TGPInitializationStrategy.INITIALIZATION_METHOD_RANDOM_BRANCH){
         initializeNotRamped(solutions, programManager);
      } else if(initializationStrategy == TGPInitializationStrategy.INITIALIZATION_METHOD_RAMPED_FULL
              || initializationStrategy == TGPInitializationStrategy.INITIALIZATION_METHOD_RAMPED_GROW){
         initializeRamped(solutions, programManager);
      } else if(initializationStrategy == TGPInitializationStrategy.INITIALIZATION_METHOD_RAMPED_HALF_HALF){
         initializeRampedHalfHalf(solutions, programManager);
      }
   }

   private static void initializeRampedHalfHalf(List<Solution> solutions, TreeGP programManager) {
      int populationSize = programManager.getPopulationSize();
      int maxDepthForCreation = programManager.getMaxDepthForCreation();
      int part_count = maxDepthForCreation - 1;

      int interval = populationSize / part_count;
      int interval2 = interval / 2;


      for (int i = 0; i < part_count; i++)
      {
         for (int j = 0; j < interval2; ++j)
         {
            Solution solution = createSolution(programManager, i+2, TGPInitializationStrategy.INITIALIZATION_METHOD_GROW);
            solutions.add(solution);
         }
         for (int j = interval2; j < interval; ++j)
         {
            Solution solution = createSolution(programManager, i+2, TGPInitializationStrategy.INITIALIZATION_METHOD_FULL);
            solutions.add(solution);
         }
      }

      int pop_count = solutions.size();

      for (int i = pop_count; i < populationSize; ++i)
      {
         Solution solution = createSolution(programManager, maxDepthForCreation, TGPInitializationStrategy.INITIALIZATION_METHOD_GROW);
         solutions.add(solution);
      }
   }

   private static void initializeRamped(List<Solution> solutions, TreeGP programManager) {
      int populationSize = programManager.getPopulationSize();
      int maxDepthForCreation = programManager.getMaxDepthForCreation();
      int part_count = maxDepthForCreation - 1;

      int interval = populationSize / part_count;

      TGPInitializationStrategy method = programManager.getInitializationStrategy();
      if(method == TGPInitializationStrategy.INITIALIZATION_METHOD_RAMPED_FULL){
         method = TGPInitializationStrategy.INITIALIZATION_METHOD_FULL;
      } else if(method == TGPInitializationStrategy.INITIALIZATION_METHOD_RAMPED_GROW){
         method = TGPInitializationStrategy.INITIALIZATION_METHOD_GROW;
      }

      for (int i = 0; i < part_count; i++)
      {
         for (int j = 0; j < interval; ++j)
         {
            Solution solution = createSolution(programManager, i+1, method);
            solutions.add(solution);
         }
      }

      int pop_count = solutions.size();

      for (int i = pop_count; i < populationSize; ++i)
      {
         Solution solution = createSolution(programManager, maxDepthForCreation, method);
         solutions.add(solution);
      }
   }


   private static void initializeNotRamped(List<Solution> solutions, TreeGP programManager) {
      int iPopulationSize = programManager.getPopulationSize();
      for (int i = 0; i < iPopulationSize; i++)
      {
         Solution solution = createSolution(programManager, programManager.getMaxDepthForCreation(), programManager.getInitializationStrategy());
         solutions.add(solution);
      }
   }

   private static Solution createSolution(TreeGP programManager, int maxDepth, TGPInitializationStrategy initializationStrategy){
      final int treeCount = programManager.getTreeCountPerSolution();
      return new Solution(treeCount, (index) -> {
         Program tree = new Program();
         tree.createWithDepth(maxDepth, programManager, initializationStrategy);
         return tree;
      });
   }



}
