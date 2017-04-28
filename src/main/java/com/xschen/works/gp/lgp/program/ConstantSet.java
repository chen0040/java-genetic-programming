package com.xschen.works.gp.lgp.program;


import com.xschen.works.gp.services.RandEngine;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 28/4/2017.
 * Containers for the read-only registers
 */
public class ConstantSet {
   private final List<Register> registers = new ArrayList<>();
   private final List<Double> weights = new ArrayList<>();

   public ConstantSet(){

   }

   public ConstantSet makeCopy(){
      ConstantSet clone = new ConstantSet();
      for(int i=0; i < registers.size(); ++i){
         clone.registers.add(registers.get(i).makeCopy());
         clone.weights.add(weights.get(i));
      }
      return clone;
   }

   public double weightSum() {
      if(weights.isEmpty()){
         return 0;
      }
      return weights.stream().reduce((a, b ) -> a + b).get();
   }

   public Register anyOther(Register excluded, RandEngine rand){
      double weightSum = weightSum();
      for(int attempts = 0; attempts < 10; attempts++){
         double r = weightSum * rand.uniform();
         double accSum = 0;
         for(int i=0; i < registers.size(); ++i){
            accSum += weights.get(i);
            if(r <= accSum){
               if(registers.get(i) == excluded){
                  break;
               } else {
                  return registers.get(i);
               }
            }
         }
      }

      return excluded;
   }

   public Register get(int index){
      return registers.get(index);
   }

   public int size() {
      return registers.size();
   }

   public void add(Register register, double weight) {
      register.setConstant(true);
      register.setIndex(registers.size());
      registers.add(register);
      weights.add(weight);
   }

   public void add(double value, double weight){
      Register register = new Register();
      register.setValue(value);
      add(register, weight);
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();

      for(int i=0; i < registers.size(); ++i){
         if(i != 0){
            sb.append("\r\n");
         }
         sb.append("register[").append(i).append("]: ").append(registers.get(i));
      }
      return sb.toString();
   }


   @Override public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      ConstantSet that = (ConstantSet) o;

      if (!registers.equals(that.registers))
         return false;
      return weights.equals(that.weights);

   }


   @Override public int hashCode() {
      int result = registers.hashCode();
      result = 31 * result + weights.hashCode();
      return result;
   }
}
