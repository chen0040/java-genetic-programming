package com.github.chen0040.gp.lgp.program;


import com.github.chen0040.gp.lgp.program.operators.IfLessThan;
import com.github.chen0040.gp.lgp.program.operators.IfGreaterThan;

import java.io.Serializable;


/**
 * Created by xschen on 29/4/2017.
 */
public class OperatorSet extends EntityContainer<Operator> implements Serializable {
   private static final long serialVersionUID = -4955023847564430563L;

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
}
