package com.github.chen0040.gp.treegp.gp;


import com.github.chen0040.gp.services.RandEngine;
import com.github.chen0040.gp.treegp.TreeGP;
import com.github.chen0040.gp.treegp.program.Primitive;
import com.github.chen0040.gp.treegp.program.Program;
import com.github.chen0040.gp.treegp.program.Solution;
import com.github.chen0040.gp.treegp.program.TreeNode;

import java.util.List;


/**
 * Created by xschen on 17/5/2017.
 */
public class MicroMutation {
   /// <summary>
   /// Method that implements the "Point Mutation" described in Section 2.4 of "A Field Guide to Genetic Programming"
   /// In Section 5.2.2 of "A Field Guide to Genetic Programming", this is also described as node replacement mutation
   /// </summary>
   public static void apply(Program program, TreeGP manager)
   {
      RandEngine randEngine = manager.getRandEngine();
      TreeNode node = program.anyNode(randEngine)._1();

      if (node.isTerminal())
      {
         Primitive terminal = program.anyTerminal(randEngine);
         int trials = 0;
         int max_trials = 50;
         while (node.getPrimitive() == terminal)
         {
            terminal = program.anyTerminal(randEngine);
            trials++;
            if (trials > max_trials) break;
         }
         if (terminal != null)
         {
            node.setPrimitive(terminal);
         }

      }
      else
      {
         int parameter_count = node.arity();
         Primitive op = program.getOperatorSet().anyOther(parameter_count, node.getPrimitive(), randEngine);
         if (op != null)
         {
            node.setPrimitive(op);
         }

      }
   }

   public static void apply(Solution solution, TreeGP manager){
      List<Program> trees = solution.getTrees();
      for(int i=0; i < trees.size(); ++i){
         apply(trees.get(i), manager);
      }
   }
}
