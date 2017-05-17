package com.github.chen0040.gp.treegp.gp;


import com.github.chen0040.data.utils.TupleTwo;
import com.github.chen0040.gp.services.RandEngine;
import com.github.chen0040.gp.treegp.TreeGP;
import com.github.chen0040.gp.treegp.enums.TGPCrossoverStrategy;
import com.github.chen0040.gp.treegp.program.Primitive;
import com.github.chen0040.gp.treegp.program.Program;
import com.github.chen0040.gp.treegp.program.Solution;
import com.github.chen0040.gp.treegp.program.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by xschen on 14/5/2017.
 */
public class Crossover {
   /// <summary>
   /// Method that implements the subtree crossover described in Section 2.4 of "A Field Guide to Genetic Programming"
   /// </summary>
   /// <param name="program1">One tree to be crossover with</param>
   /// <param name="program2">Another tree to be crossover with</param>
   /// <param name="iMaxDepthForCrossover">The maximum depth of the trees after the crossover</param>
   public static void apply(Program program1, Program program2, TreeGP manager)
   {
      int iMaxDepthForCrossover = manager.getMaxDepthForCrossover();
      TGPCrossoverStrategy method = manager.getCrossoverStrategy();

      RandEngine randEngine = manager.getRandEngine();

      if (method == TGPCrossoverStrategy.CROSSOVER_SUBTREE_BIAS || method == TGPCrossoverStrategy.CROSSVOER_SUBTREE_NO_BIAS)
      {
         boolean bias = (method == TGPCrossoverStrategy.CROSSOVER_SUBTREE_BIAS);

         int iMaxDepth1 = program1.calcDepth();
         int iMaxDepth2 = program2.calcDepth();

         TupleTwo<TreeNode, TreeNode> pCutPoint1;
         TupleTwo<TreeNode, TreeNode> pCutPoint2;

         boolean is_crossover_performed = false;
         // Suppose that at the beginning both the current GP and the other GP do not violate max depth constraint
         // then try to see whether a crossover can be performed in such a way that after the crossover, both GP still have depth <= max depth
         if (iMaxDepth1 <= iMaxDepthForCrossover && iMaxDepth2 <= iMaxDepthForCrossover)
         {
            int max_trials = 50;
            int trials = 0;
            do
            {
               pCutPoint1 = program1.anyNode(bias, randEngine);
               pCutPoint2 = program2.anyNode(bias, randEngine);


               if (pCutPoint1 != null && pCutPoint2 != null)
               {
                  TupleTwo<TupleTwo<TreeNode, TreeNode>, TupleTwo<TreeNode, TreeNode>> result = swap(program1, program2, pCutPoint1, pCutPoint2);

                  iMaxDepth1 = program1.calcDepth();
                  iMaxDepth2 = program2.calcDepth();

                  if (iMaxDepth1 <= iMaxDepthForCrossover && iMaxDepth2 <= iMaxDepthForCrossover) //crossover is successful
                  {
                     is_crossover_performed = true;
                     break;
                  }
                  else
                  {
                     TupleTwo<TreeNode, TreeNode> newCutPoint1 = result._1();
                     TupleTwo<TreeNode, TreeNode> newCutPoint2 = result._2();

                     swap(program1, program2, newCutPoint1, newCutPoint2); // swap back so as to restore to the original GP trees if the crossover is not valid due to max depth violation
                  }
               }

               trials++;
            } while (trials < max_trials);
         }

         // force at least one crossover even if the maximum depth is violated above so that this operator won't end up like a reproduction operator
         if (!is_crossover_performed)
         {
            pCutPoint1 = program1.anyNode(bias, randEngine);
            pCutPoint2 = program2.anyNode(bias, randEngine);

            if (pCutPoint1 != null && pCutPoint2 != null)
            {
               swap(program1, program2, pCutPoint1, pCutPoint2);


               program1.calcLength();
               program2.calcLength();

            }
         }
      }
   }


   private static TupleTwo<TupleTwo<TreeNode, TreeNode>, TupleTwo<TreeNode, TreeNode>> swap(Program program1, Program program2, TupleTwo<TreeNode, TreeNode> cutPoint1, TupleTwo<TreeNode, TreeNode> cutPoint2) {
      TreeNode parent1 = cutPoint1._2();
      TreeNode parent2 = cutPoint2._2();
      
      TreeNode point1 = cutPoint1._1();
      TreeNode point2 = cutPoint2._1();

      if (parent1 == null || parent2 == null)
      {
         Primitive content1 = point1.getPrimitive();
         Primitive content2 = point2.getPrimitive();
         point1.setPrimitive(program1.matchPrimitive(content2));
         point2.setPrimitive(program2.matchPrimitive(content1));
         List<TreeNode> children1 = new ArrayList<>(point1.getChildren());
         List<TreeNode> children2 = new ArrayList<>(point2.getChildren());
         point1.getChildren().clear();
         point2.getChildren().clear();
         for (int i = 0; i < children1.size(); ++i)
         {
            point2.getChildren().add(children1.get(i).makeCopy(program2.getOperatorSet(), program2.getVariableSet(), program2.getConstantSet()));
         }
         for (int i = 0; i < children2.size(); ++i)
         {
            point1.getChildren().add(children2.get(i).makeCopy(program1.getOperatorSet(), program1.getVariableSet(), program1.getConstantSet()));
         }
         return new TupleTwo<>(cutPoint1, cutPoint2);
      }
      else
      {
         int child_index1 = parent1.getChildren().indexOf(point1);
         int child_index2 = parent2.getChildren().indexOf(point2);

         TreeNode newChild1 = point2.makeCopy(program1.getOperatorSet(), program1.getVariableSet(), program1.getConstantSet());
         TreeNode newChild2 = point1.makeCopy(program2.getOperatorSet(), program2.getVariableSet(), program2.getConstantSet());

         parent1.getChildren().set(child_index1, newChild1);
         parent2.getChildren().set(child_index2, newChild2);

         return new TupleTwo<>(new TupleTwo<>(newChild1, parent1), new TupleTwo<>(newChild2, parent2));
      }
   }

   public static void apply(Solution solution1, Solution solution2, TreeGP manager)
   {
      RandEngine randEngine = manager.getRandEngine();

      int tree_count = solution1.getTrees().size();
      for (int i = 0; i < tree_count; ++i)
      {
         if (tree_count > 1 && randEngine.uniform() < 0.5)
         {
            Program temp = solution2.getTrees().get(i);
            solution2.getTrees().set(i, solution1.getTrees().get(i));
            solution1.getTrees().set(i, temp);
         }
         else
         {
            apply(solution1.getTrees().get(i), solution2.getTrees().get(i), manager);
         }

      }

      solution1.invalidateCost();
      solution2.invalidateCost();
   }

}
