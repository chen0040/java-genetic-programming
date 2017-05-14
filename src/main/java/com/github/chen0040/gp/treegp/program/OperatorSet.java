package com.github.chen0040.gp.treegp.program;


import com.github.chen0040.gp.commons.EntityContainer;
import com.github.chen0040.gp.services.RandEngine;
import com.github.chen0040.gp.treegp.program.operators.IfGreaterThan;
import com.github.chen0040.gp.treegp.program.operators.IfLessThan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by xschen on 29/4/2017.
 */
public class OperatorSet extends EntityContainer<Primitive> implements Serializable {

   private static final long serialVersionUID = -6660969377144991417L;


   public OperatorSet(){

   }

   public boolean isReadOnly(){
      return false;
   }

   public OperatorSet makeCopy(){
      OperatorSet clone = new OperatorSet();
      clone.copy(this);
      return clone;
   }

   public void addIfLessThanOperator() {
      addIfLessThanOperator(1.0);
   }

   public void addIfLessThanOperator(double weight)
   {
      add(new IfLessThan(), weight);
   }

   public void addIfGreaterThanOperator() {
      addIfGreaterThanOperator(1.0);
   }

   public void addIfGreaterThanOperator(double weight)
   {
      add(new IfGreaterThan(), weight);
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < entities.size(); ++i){
         if(i != 0){
            sb.append("\r\n");
         }
         sb.append("operator[").append(i).append("]: ").append(entities.get(i));
      }
      return sb.toString();
   }

   public Primitive anyOther(int arity, Primitive excluded, RandEngine rand){

      List<Primitive> candidates = new ArrayList<>();
      double weightSum = 0;
      for(int i=0; i < entities.size(); ++i){
         Primitive op = entities.get(i);
         if(op.arity()==arity){
            weightSum += weights.get(op.getIndex());
            candidates.add(op);
         }
      }

      for(int attempts = 0; attempts < 10; attempts++){
         double r = weightSum * rand.uniform();
         double accSum = 0;

         for(int i = 0; i < candidates.size(); ++i){
            Primitive op = candidates.get(i);
            accSum += weights.get(op.getIndex());
            if(r <= accSum){
               if(op == excluded){
                  break;
               } else {
                  return op;
               }
            }
         }
      }

      return excluded;
   }

   /// <summary>
   /// Method that is used by the "RandomBranch" initialization algorithm to obtain a random function node with arity less than s
   /// </summary>
   /// <param name="maxArity">The tree size</param>
   /// <returns></returns>
   public Primitive anyWithArityLessThan(int maxArity, RandEngine randEngine)
   {
      List<Primitive> ops = new ArrayList<>();
      for (int i = 0; i < entities.size(); ++i)
      {
         Primitive op1 = entities.get(i);
         if (op1.arity() < maxArity)
         {
            ops.add(op1);
         }
      }
      if (ops.isEmpty()) return null;
      return ops.get(randEngine.nextInt(ops.size()));
   }
}
