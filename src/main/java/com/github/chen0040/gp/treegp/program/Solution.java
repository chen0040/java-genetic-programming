package com.github.chen0040.gp.treegp.program;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


/**
 * Created by xschen on 14/5/2017.
 */
public class Solution {
   private double cost;
   private boolean costValid;
   private final List<Program> trees = new ArrayList<>();

   public Solution() {

   }

   public Solution(int treeCount, Supplier<Program> generator){
      for (int i = 0; i < treeCount; ++i)
      {
         trees.add(generator.get());
      }
   }

   public Solution makeCopy()
   {
      Solution clone = new Solution();
      clone.copy(this);
      return clone;
   }

   public void copy(Solution that){
      trees.clear();
      for(int i=0; i < that.trees.size(); ++i){
         trees.add(that.trees.get(i).makeCopy());
      }
      cost = that.cost;
      costValid = that.costValid;
   }

   @Override
   public String toString()
   {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < trees.size(); ++i)
      {
         if (i != 0)
         {
            sb.append("\n");
         }
         sb.append("Trees[").append(i).append("]: ").append(trees.get(i));
      }
      return sb.toString();
   }

   public String mathExpression()
   {
         StringBuilder sb = new StringBuilder();
         for (int i = 0; i < trees.size(); ++i)
         {
            if (i != 0)
            {
               sb.append("\n");
            }
            sb.append("Trees[").append(i).append("]: ").append(trees.get(i).mathExpression());
         }
         return sb.toString();
   }

   public void invalidateCost(){
      costValid = false;
   }
}
