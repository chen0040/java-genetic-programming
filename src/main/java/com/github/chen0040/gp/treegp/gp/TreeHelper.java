package com.github.chen0040.gp.treegp.gp;


import com.github.chen0040.gp.services.RandEngine;
import com.github.chen0040.gp.treegp.TreeGP;
import com.github.chen0040.gp.treegp.enums.TGPInitializationStrategy;
import com.github.chen0040.gp.treegp.program.OperatorSet;
import com.github.chen0040.gp.treegp.program.Primitive;
import com.github.chen0040.gp.treegp.program.Program;
import com.github.chen0040.gp.treegp.program.TreeNode;


/**
 * Created by xschen on 14/5/2017.
 */
public class TreeHelper {

   /// <summary>
   /// Method that creates a subtree of maximum depth
   /// </summary>
   /// <param name="pRoot">The root node of the subtree</param>
   /// <param name="allowableDepth">The maximum depth</param>
   /// <param name="method">The method used to build the subtree</param>
   public static void createWithDepth(Program program, TreeNode x, int allowableDepth, TGPInitializationStrategy method, RandEngine randEngine) {

      int child_count = x.arity();

      for (int i = 0; i != child_count; ++i)
      {
         Primitive primitive = anyPrimitive(program, allowableDepth, method, randEngine);
         TreeNode child = new TreeNode(primitive);
         x.getChildren().add(child);

         if (!primitive.isTerminal())
         {
            createWithDepth(program, child, allowableDepth - 1, method, randEngine);
         }
      }
   }

   /// <summary>
   /// Method that creates a GP tree with a maximum tree depth
   /// </summary>
   /// <param name="manager">TreeGP config</param>
   public static TreeNode createWithDepth(Program program, int allowableDepth, TreeGP manager)
   {
      TGPInitializationStrategy method = manager.getInitializationStrategy();
      OperatorSet operatorSet = program.getOperatorSet();
      RandEngine randEngine = manager.getRandEngine();

      TreeNode root;

      //  Population Initialization method following the "RandomBranch" method described in "Kumar Chellapilla. Evolving computer programs without subtree crossover. IEEE Transactions on Evolutionary Computation, 1(3):209–216, September 1997."
      if (method == TGPInitializationStrategy.INITIALIZATION_METHOD_RANDOMBRANCH)
      {
         int s = allowableDepth; //tree size
         Primitive non_terminal = program.anyOperatorWithArityLessThan(s, randEngine);
         if (non_terminal == null)
         {
            root = new TreeNode(program.anyTerminal(randEngine));
         }
         else
         {
            root = new TreeNode(non_terminal);
            int b_n = non_terminal.arity();
            s = (int)Math.floor((double)s / b_n);
            randomBranch(program, root, s, randEngine);
         }
      }
      // Population Initialization method following the "PTC1" method described in "Sean Luke. Two fast tree-creation algorithms for genetic programming. IEEE Transactions in Evolutionary Computation, 4(3), 2000b."
      else if(method==TGPInitializationStrategy.INITIALIZATION_METHOD_PTC1)
      {
         // TODO: Change this one later back to use tag
         int expectedTreeSize = 20; //Convert.ToInt32(tag);

         int b_n_sum=0;
         for(int i=0; i < operatorSet.size(); ++i)
         {
            b_n_sum+=operatorSet.get(i).arity();
         }
         double p= (1- 1.0 / expectedTreeSize) / ((double)b_n_sum / operatorSet.size());

         Primitive data = null;
         if(randEngine.uniform() <= p)
         {
            data = program.anyOperator(randEngine);
         }
         else
         {
            data = program.anyTerminal(randEngine);
         }

         root = new TreeNode(data);
         PTC1(program, root, p, allowableDepth-1, randEngine);


      }
      else // handle full and grow method
      {
         root = new TreeNode(anyPrimitive(program, allowableDepth, method, randEngine));

         createWithDepth(program, root, allowableDepth - 1, method, randEngine);
      }

      return root;
   }

   /// <summary>
   /// Population Initialization Method described in "Kumar Chellapilla. Evolving computer programs without subtree crossover. IEEE Transactions on Evolutionary Computation, 1(3):209–216, September 1997."
   /// </summary>
   /// <param name="parent_node"></param>
   /// <param name="s"></param>
   /// <param name="?"></param>
   private static void randomBranch(Program program, TreeNode x, int s, RandEngine randEngine)
   {
      int child_count = x.arity();

      for (int i = 0; i != child_count; i++)
      {
         Primitive non_terminal= program.anyOperatorWithArityLessThan(s, randEngine);
         if (non_terminal == null)
         {
            TreeNode child = new TreeNode(program.anyTerminal(randEngine));
            x.getChildren().add(child);
         }
         else
         {
            TreeNode child = new TreeNode(non_terminal);
            x.getChildren().add(child);
            int b_n=non_terminal.arity();
            int s_pi = (int)Math.floor((double)s / b_n);
            randomBranch(program, child, s_pi, randEngine);
         }
      }
   }

   /// <summary>
   /// Population Initialization method following the "PTC1" method described in "Sean Luke. Two fast tree-creation algorithms for genetic programming. IEEE Transactions in Evolutionary Computation, 4(3), 2000b."
   /// </summary>
   /// <param name="parent_node">The node for which the child nodes are generated in this method</param>
   /// <param name="p">expected probability</param>
   /// <param name="allowableDepth">The maximum tree depth</param>
   private static void PTC1(Program program, TreeNode parent_node, double p, int allowableDepth, RandEngine randEngine)
   {
      int child_count = parent_node.arity();

      for (int i = 0; i != child_count; i++)
      {
         Primitive data;
         if (allowableDepth == 0)
         {
            data = program.anyTerminal(randEngine);
         }
         else if (randEngine.uniform() <= p)
         {
            data = program.anyOperator(randEngine);
         }
         else
         {
            data = program.anyTerminal(randEngine);
         }

         TreeNode child = new TreeNode(data);
         parent_node.getChildren().add(child);

         if(!data.isTerminal())
         {
            PTC1(program, child, p, allowableDepth - 1, randEngine);
         }
      }
   }



   /// <summary>
   /// Method that follows the implementation of GP initialization in Algorithm 2.1 of "A Field Guide to Genetic Programming"
   /// </summary>
   /// <param name="allowableDepth">Maximum depth of the GP tree</param>
   /// <param name="method">The initialization method, currently either "Grow" or "Full"</param>
   /// <returns></returns>
   public static Primitive anyPrimitive(Program program, int allowableDepth, TGPInitializationStrategy method, RandEngine randEngine)
   {
      int terminal_count = (program.getVariableSet().size() + program.getConstantSet().size());
      int function_count = program.getOperatorSet().size();

      double terminal_prob=(double)terminal_count / (terminal_count + function_count);
      if (allowableDepth <= 0 || (method == TGPInitializationStrategy.INITIALIZATION_METHOD_GROW && randEngine.uniform() <= terminal_prob))
      {
         return program.anyTerminal(randEngine);
      }
      else
      {
         return program.anyOperator(randEngine);
      }
   }


}
