package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.treegp.program.Operator;
import com.github.chen0040.gp.treegp.program.Primitive;


/**
 * Created by xschen on 11/5/2017.
 */
public class Not extends Operator {
   private static final double tolerance = 0.0000001;
   private static final long serialVersionUID = 346204193784368825L;


   public Not(){
      super(1, "NOT");
   }

   @Override public void execute(Object... tags) {
      setValue(isTrue(getInput(0)) ? 1.0 : 0.0);
   }

   public boolean isTrue(double x)
   {
      if (x > -tolerance && x < tolerance)
      {
         return false;
      }
      return true;
   }


   @Override public Primitive makeCopy() {
      return new Not().copy(this);
   }
}
