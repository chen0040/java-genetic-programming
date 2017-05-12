package com.github.chen0040.gp.treegp.program;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 12/5/2017.
 */
@Getter
@Setter
public class TreeNode {
   private Primitive primitive;

   @Setter(AccessLevel.NONE)
   private final List<TreeNode> children = new ArrayList<>();

   public int arity(){
      return primitive.arity();
   }

   public int length(){
      int lengthSoFar = 1;
      for(TreeNode node : children){
         lengthSoFar+=node.length();
      }
      return lengthSoFar;
   }

   public int depth(){
      return depth(this, 0);
   }

   private static int depth(TreeNode node, int depthSoFar){
      int maxDepth = depthSoFar;
      for(TreeNode child : node.children){
         int depth = depth(child, depthSoFar+1);
         maxDepth = Math.max(maxDepth, depth);
      }
      return maxDepth;
   }

   public double execute(Object... tags) {
      int count = arity();
      List<Double> inputs = new ArrayList<>();
      for(int i=0; i <count; ++i){
         inputs.add(children.get(i).execute(tags));
      }
      primitive.read(inputs);
      primitive.execute(tags);
      return primitive.getValue();
   }

   public TreeNode makeCopy(){
      TreeNode clone = new TreeNode();
      clone.setPrimitive(this.primitive);
      for(TreeNode child : children){
         clone.children.add(child.makeCopy());
      }
      return clone;
   }

   public boolean isTerminal(){
      return primitive.isTerminal();
   }

   @Override
   public String toString()
   {
      if (primitive.isTerminal())
      {
         return primitive.toString();
      }
      else
      {
         StringBuilder sb = new StringBuilder();
         sb.append("(").append(primitive.getSymbol());
         for (int i = 0; i < arity(); ++i)
         {
            if(i != 0){
               sb.append(", ");
            }
            sb.append(children.get(i));
         }
         sb.append(")");
         return sb.toString();
      }

   }

   public String mathExpression()
   {
      if (primitive.isTerminal())
      {
         return primitive.toString();
      }
      else
      {
         int arity = arity();
         StringBuilder sb = new StringBuilder();
         if (arity == 1)
         {
            sb.append(primitive.getSymbol()).append("(").append(children.get(0).mathExpression()).append(")");

         }
         else if (arity == 2)
         {
            if (children.get(0).isTerminal())
            {
               sb.append(children.get(0).mathExpression());

            }
            else
            {
               sb.append("(").append(children.get(0).mathExpression()).append(")");
            }
            sb.append(" ").append(primitive.getSymbol()).append(" ");
            if (children.get(1).isTerminal())
            {
               sb.append(children.get(1).mathExpression());

            }
            else
            {
               sb.append("(").append(children.get(1).mathExpression()).append(")");
            }
         }
         else if (arity == 4)
         {

            String symbol = primitive.getSymbol();
            if (symbol.equals("if<") || symbol.equals("if>"))
            {

               sb.append("if(").append(children.get(0).mathExpression())
                       .append(symbol.equals("if<") ? " < " : " > ").append(children.get(1).mathExpression())
                       .append(", ").append(children.get(2).mathExpression())
                       .append(", ").append(children.get(3).mathExpression())
                       .append(")");
            }
            else
            {
               sb.append("(").append(symbol);
               sb.append(" ");
               for (int i = 0; i < arity; ++i)
               {
                  if (i != 0)
                  {
                     sb.append(", ");
                  }

                  sb.append(children.get(i).mathExpression());
               }
               sb.append(")");
            }

         }
         else
         {
            sb.append("(").append(primitive.getSymbol());
            sb.append(" ");
            for (int i = 0; i < arity; ++i)
            {
               if (i != 0)
               {
                  sb.append(", ");
               }

               sb.append(children.get(i).mathExpression());
            }
            sb.append(")");
         }

         return sb.toString();
      }
   }

   public List<Primitive> flatten(){
      List<Primitive> result = new ArrayList<>();
      collect(this, result);
      return result;
   }

   private void collect(TreeNode node, List<Primitive> list) {
      list.add(node.primitive);
      for(TreeNode child : node.children){
         collect(child, list);
      }
   }

   public List<TreeNode> flattenNodes(){
      List<TreeNode> result = new ArrayList<>();
      collectNodes(this, result);
      return result;
   }

   private void collectNodes(TreeNode node, List<TreeNode> list) {
      list.add(node);
      for(TreeNode child : node.children){
         collectNodes(child, list);
      }
   }

   public int depth2Node(TreeNode node){
      return depth2Node(this, node, 0);
   }


   public static int depth2Node(TreeNode node, TreeNode target, int depthSoFar)
   {
      if (node == target)
      {
         return depthSoFar;
      }

      int maxDepthOfChild = -1;
      for (TreeNode child_node :node.children)
      {
         int d = depth2Node(child_node, target, depthSoFar + 1);
         if (d > maxDepthOfChild)
         {
            maxDepthOfChild = d;
         }
      }

      return maxDepthOfChild;
   }



}
