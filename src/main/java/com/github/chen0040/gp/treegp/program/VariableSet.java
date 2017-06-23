package com.github.chen0040.gp.treegp.program;


import com.github.chen0040.gp.commons.EntityContainer;

import java.io.Serializable;


/**
 * Created by xschen on 12/5/2017.
 */
public class VariableSet extends EntityContainer<Primitive> implements Serializable {

   private static final long serialVersionUID = 8059994703104333304L;


   public boolean isReadOnly(){
      return false;
   }

   public VariableSet makeCopy(){
      VariableSet clone = new VariableSet();
      clone.copy(this);
      return clone;
   }

   public void add(String symbol, double value, double weight){
      Terminal register = new Terminal(symbol, value, "", isReadOnly());
      add(register, weight);
   }

   public void add(String symbol, String value, double weight) {
      Terminal register = new Terminal(symbol, 0, value, isReadOnly());
      add(register, weight);
   }

   public void set(String symbol, double value) {
      get(symbol).setValue(value);
   }

   public void set(String symbol, String value) {
      get(symbol).setValue(value);
   }

   public void set(int index, double value) {
      get(index).setValue(value);
   }

   public void set(int index, String value) {
      get(index).setValue(value);
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
