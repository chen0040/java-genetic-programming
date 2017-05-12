package com.github.chen0040.gp.treegp.program;


/**
 * Created by xschen on 28/4/2017.
 * Containers for the read-only registers
 */
public class ConstantSet extends VariableSet {
   public ConstantSet(){
      super();
   }

   @Override
   public boolean isReadOnly(){
      return true;
   }

   @Override
   public VariableSet makeCopy(){
      VariableSet clone = new ConstantSet();
      clone.copy(this);
      return clone;
   }
}
