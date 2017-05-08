package com.github.chen0040.gp.treegp.program;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 8/5/2017.
 */
public class Primitive {
   private final List<Double> inputs = new ArrayList<>();
   private double value;

   public boolean isTerminal(){
      return inputs.isEmpty();
   }

   public Primitive(){

   }

   public Primitive(int inputCount) {
      for(int i=0; i < inputCount; ++i){
         inputs.add(0.0);
      }
   }

   public int arity(){
      return inputs.size();
   }

   public void fire(List<Double> values){
      if(inputs.size() != values.size()) {
         throw new RuntimeException("Value size not matched");
      }
      for(int i=0; i < values.size(); ++i) {
         inputs.set(i, values.get(i));
      }
   }

   public void setInput(int index, double value){
      inputs.set(index, value);
   }

   public double getInput(int index) {
      return inputs.get(index);
   }




}
