package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.treegp.program.Operator;
import com.github.chen0040.gp.treegp.program.Primitive;


/**
 * Created by xschen on 11/5/2017.
 */
public class Or extends Operator {
   private static final double mTolerance = 0.0000001;
   private static final long serialVersionUID = 8437638693719434555L;


   public Or(){
      super(2, "OR");
   }

   @Override public void execute(Observation observation)       {
      double x1 = this.getInput(0);
      double x2 = this.getInput(1);
      if (isTrue(x1) || isTrue(x2))
      {
         setValue(1);
      }
      else
      {
         setValue(0);
      }
   }

   private boolean isTrue(double x)
   {
      if (x > -mTolerance && x < mTolerance)
      {
         return false;
      }
      return true;
   }


   @Override public Primitive makeCopy() {
      return new Or().copy(this);
   }
}
