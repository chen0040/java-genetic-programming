package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.treegp.program.Operator;
import com.github.chen0040.gp.treegp.program.Primitive;


/**
 * Created by xschen on 11/5/2017.
 */
public class IfGreaterThan extends Operator {
   private static final long serialVersionUID = -2258019380394265659L;


   public IfGreaterThan() {
      super(4, "if>");

   }


   @Override
   public void execute(Observation observation)
   {
      setValue(0);
      if (this.getInput(0) > this.getInput(1))
      {
         setValue(this.getInput(2));
      }
      else
      {
         setValue(this.getInput(3));
      }
   }


   @Override public Primitive makeCopy() {
      return new IfGreaterThan().copy(this);
   }
}
