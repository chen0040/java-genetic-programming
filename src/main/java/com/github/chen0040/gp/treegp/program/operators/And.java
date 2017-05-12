package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.treegp.program.Operator;


/**
 * Created by xschen on 11/5/2017.
 */
public class And extends Operator {
   private static final double mTolerance = 0.0000001;
   private static final long serialVersionUID = 7814710401617777968L;

   public And(){
      super(2, "AND");
   }

   @Override public void execute(Object... tags)        {
      double x1 = this.getInput(0);
      double x2 = this.getInput(1);
      if (isTrue(x1) && isTrue(x2))
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


   @Override public Operator makeCopy() {
      return new And().copy(this);
   }
}
