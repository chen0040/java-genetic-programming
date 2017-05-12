package com.github.chen0040.gp.treegp.program;


import com.github.chen0040.gp.commons.EntityContainer;


/**
 * Created by xschen on 12/5/2017.
 */
public class VariableSet extends EntityContainer<Terminal> {

   public boolean isReadOnly(){
      return false;
   }

   public VariableSet makeCopy(){
      VariableSet clone = new VariableSet();
      clone.copy(this);
      return clone;
   }

   public void add(String symbol, double value, double weight){
      Terminal register = new Terminal(symbol, value, isReadOnly());
      add(register, weight);
   }


   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < entities.size(); ++i){
         if(i != 0){
            sb.append("\r\n");
         }
         if(isReadOnly()){
            sb.append("constant");
         } else {
            sb.append("register");
         }
         sb.append("[").append(i).append("]: ").append(entities.get(i));
      }
      return sb.toString();
   }
}
