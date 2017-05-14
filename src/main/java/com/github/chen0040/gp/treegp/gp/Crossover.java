package com.github.chen0040.gp.treegp.gp;


import com.github.chen0040.data.utils.TupleTwo;
import com.github.chen0040.gp.services.RandEngine;
import com.github.chen0040.gp.treegp.TreeGP;
import com.github.chen0040.gp.treegp.enums.TGPCrossoverStrategy;
import com.github.chen0040.gp.treegp.program.Program;
import com.github.chen0040.gp.treegp.program.TreeNode;


/**
 * Created by xschen on 14/5/2017.
 */
public class Crossover {
   /// <summary>
   /// Method that implements the subtree crossover described in Section 2.4 of "A Field Guide to Genetic Programming"
   /// </summary>
   /// <param name="rhs">Another tree to be crossover with</param>
   /// <param name="iMaxDepthForCrossover">The maximum depth of the trees after the crossover</param>
   public static void apply(Program it, Program rhs, TreeGP manager)
   {
      int iMaxDepthForCrossover = manager.getMaxDepthForCrossover();
      TGPCrossoverStrategy method = manager.getCrossoverStrategy();

      RandEngine randEngine = manager.getRandEngine();

      if (method == TGPCrossoverStrategy.CROSSOVER_SUBTREE_BIAS || method == TGPCrossoverStrategy.CROSSVOER_SUBTREE_NO_BIAS)
      {
         boolean bias = (method == TGPCrossoverStrategy.CROSSOVER_SUBTREE_BIAS);

         int iMaxDepth1 = it.calcDepth();
         int iMaxDepth2 = rhs.calcDepth();

         TupleTwo<TreeNode, TreeNode> pCutPoint1 = null;
         TupleTwo<TreeNode, TreeNode> pCutPoint2 = null;

         boolean is_crossover_performed = false;
         // Suppose that at the beginning both the current GP and the other GP do not violate max depth constraint
         // then try to see whether a crossover can be performed in such a way that after the crossover, both GP still have depth <= max depth
         if (iMaxDepth1 <= iMaxDepthForCrossover && iMaxDepth2 <= iMaxDepthForCrossover)
         {
            int max_trials = 50;
            int trials = 0;
            do
            {
               pCutPoint1 = it.anyNode(bias, randEngine);
               pCutPoint2 = rhs.anyNode(bias, randEngine);

               if (pCutPoint1 != null && pCutPoint2 != null)
               {
                  swap(it, rhs, pCutPoint1, pCutPoint2);

                  iMaxDepth1 = it.calcDepth();
                  iMaxDepth2 = rhs.calcDepth();

                  if (iMaxDepth1 <= iMaxDepthForCrossover && iMaxDepth2 <= iMaxDepthForCrossover) //crossover is successful
                  {
                     is_crossover_performed = true;
                     break;
                  }
                  else
                  {
                     swap(it, rhs, pCutPoint1, pCutPoint2); // swap back so as to restore to the original GP trees if the crossover is not valid due to max depth violation
                  }
               }

               trials++;
            } while (trials < max_trials);
         }

         // force at least one crossover even if the maximum depth is violated above so that this operator won't end up like a reproduction operator
         if (!is_crossover_performed)
         {
            pCutPoint1 = it.anyNode(bias, randEngine);
            pCutPoint2 = rhs.anyNode(bias, randEngine);

            if (pCutPoint1 != null && pCutPoint2 != null)
            {
               swap(it, rhs, pCutPoint1, pCutPoint2);


               it.calcLength();
               rhs.calcLength();

            }
         }
      }
   }


   private static void swap(Program program1, Program program2, TupleTwo<TreeNode, TreeNode> cutPoint1, TupleTwo<TreeNode, TreeNode> cutPoint2) {

   }

}
