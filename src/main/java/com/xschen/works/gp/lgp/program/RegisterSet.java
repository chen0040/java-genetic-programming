package com.xschen.works.gp.lgp.program;


import java.io.Serializable;


/**
 * Created by xschen on 28/4/2017.
 * Containers for the mutable registers
 */
public class RegisterSet extends EntityContainer<Register> implements Serializable {
   private static final long serialVersionUID = 8315754939056946190L;

   public RegisterSet(){

   }

   public boolean isReadOnly(){
      return false;
   }

   public RegisterSet makeCopy(){
      RegisterSet clone = new RegisterSet();
      clone.copy(this);
      return clone;
   }

   public void add(Register register, double weight) {
      register.setConstant(isReadOnly());
      super.add(register, weight);
   }

   public void add(double value, double weight){
      Register register = new Register();
      register.setValue(value);
      add(register, weight);
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < entities.size(); ++i){
         if(i != 0){
            sb.append("\r\n");
         }
         sb.append("register[").append(i).append("]: ").append(entities.get(i));
      }
      return sb.toString();
   }



}
