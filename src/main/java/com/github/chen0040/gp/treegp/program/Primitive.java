package com.github.chen0040.gp.treegp.program;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 8/5/2017.
 */
public class Primitive implements Serializable {
   private static final long serialVersionUID = -249257238928605728L;
   private final List<Double> inputs = new ArrayList<>();
   private double value;
   private final String symbol;
   private final boolean readOnly;

   public boolean isTerminal(){
      return inputs.isEmpty();
   }

   public Primitive(){
      symbol = "";
      readOnly = false;
   }

   public Primitive(int inputCount, String symbol, double value, boolean readOnly) {
      for(int i=0; i < inputCount; ++i){
         inputs.add(0.0);
      }
      this.symbol = symbol;
      this.value = value;
      this.readOnly = readOnly;
   }

   public int arity(){
      return inputs.size();
   }

   public void read(List<Double> values){
      if(inputs.size() != values.size()) {
         throw new RuntimeException("Value size not matched");
      }
      for(int i=0; i < values.size(); ++i) {
         inputs.set(i, values.get(i));
      }
   }

   public void setInput(int index, double value){
      if(index >= arity()){
         throw new IndexOutOfBoundsException(index + " is greater or equal to input size" + arity());
      }
      inputs.set(index, value);
   }

   public double getInput(int index) {
      if(index >= arity()){
         throw new IndexOutOfBoundsException(index + " is greater or equal to input size" + arity());
      }
      return inputs.get(index);
   }

   public void setValue(double val){
      if(readOnly){
         throw new RuntimeException("The primitive is readonly");
      }
      value = val;
   }

   public double getValue(){
      return value;
   }


   public String getSymbol(){
      return symbol;
   }

   public boolean isReadOnly(){
      return readOnly;
   }


}
