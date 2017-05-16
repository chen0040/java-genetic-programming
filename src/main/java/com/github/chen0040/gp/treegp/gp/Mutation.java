package com.github.chen0040.gp.treegp.gp;


import com.github.chen0040.gp.services.RandEngine;
import com.github.chen0040.gp.treegp.TreeGP;
import com.github.chen0040.gp.treegp.enums.TGPInitializationStrategy;
import com.github.chen0040.gp.treegp.enums.TGPMutationStrategy;
import com.github.chen0040.gp.treegp.program.Program;
import com.github.chen0040.gp.treegp.program.Solution;
import com.github.chen0040.gp.treegp.program.TreeGenerator;
import com.github.chen0040.gp.treegp.program.TreeNode;

import java.util.List;


/**
 * Created by xschen on 14/5/2017.
 */
public class Mutation {
   /// <summary>
   /// Method that implements the subtree mutation or "headless chicken" crossover described in Section 2.4 of "A Field Guide to Genetic Programming"
   /// </summary>
   /// <param name="iMaxProgramDepth">The max depth of the tree after the mutation</param>
   public static void apply(Program program, TreeGP manager)
   {
      int iMaxProgramDepth = manager.getMaxProgramDepth();
      TGPMutationStrategy method = manager.getMutationStrategy();
      RandEngine randEngine = manager.getRandEngine();
      
      if (method == TGPMutationStrategy.MUTATION_SUBTREE || method == TGPMutationStrategy.MUTATION_SUBTREE_KINNEAR)
      {
         TreeNode node = program.anyNode(randEngine)._1();
         int depth = program.calcDepth();

         TreeNode root = program.getRoot();

         if (method == TGPMutationStrategy.MUTATION_SUBTREE)
         {
            int node_depth = root.depth2Node(node);
            node.getChildren().clear();

            node.setPrimitive(program.anyPrimitive(randEngine));

            if (!node.getPrimitive().isTerminal())
            {
               int max_depth = iMaxProgramDepth - node_depth;
               TreeGenerator.createWithDepth(program, node, max_depth, TGPInitializationStrategy.INITIALIZATION_METHOD_GROW, randEngine);
            }
         }
         else
         {
            int subtree_depth = root.depth2Node(node);
            int current_depth = depth - subtree_depth;
            int max_depth = (int)(depth * 1.15) - current_depth;

            node.getChildren().clear();
            node.setPrimitive(program.anyPrimitive(randEngine));

            if (!node.getPrimitive().isTerminal())
            {
               TreeGenerator.createWithDepth(program, node, max_depth, TGPInitializationStrategy.INITIALIZATION_METHOD_GROW, randEngine);
            }
         }
      }
      else if (method == TGPMutationStrategy.MUTATION_HOIST)
      {
         TreeNode node = program.anyNode(randEngine)._1();

         if (node != program.getRoot())
         {
            program.setRoot(node);
         }
      }
      else if (method == TGPMutationStrategy.MUTATION_SHRINK)
      {
         TreeNode node = program.anyNode(randEngine)._1();
         node.getChildren().clear();
         node.setPrimitive(program.anyTerminal(randEngine));
      }

      program.calcDepth();
      program.calcLength();
   }

   public static void apply(Solution solution, TreeGP manager)
   {
      List<Program> trees = solution.getTrees();
      int count = trees.size();
      for (int i = 0; i < count; ++i)
      {
         apply(trees.get(i), manager);
      }

      solution.invalidateCost();
   }


}
