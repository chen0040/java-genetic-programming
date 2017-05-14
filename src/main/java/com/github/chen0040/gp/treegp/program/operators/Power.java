package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.treegp.program.Operator;
import com.github.chen0040.gp.treegp.program.Primitive;


/**
 * Created by xschen on 11/5/2017.
 */
public class Power extends Operator {
   private static final long serialVersionUID = 8365660313698391735L;


   public Power(){
      super(2, "^");
   }

   @Override public void execute(Object... tags) {
      double x1 = getInput(0);
      double x2 = getInput(1);
      if (Math.abs(x1) < 10)
      {
         setValue(Math.pow(Math.abs(x1), x2));
      }
      else
      {
         setValue(x1 + x2 + 1);
      }
   }


   @Override public Primitive makeCopy() {
      return new Power().copy(this);
   }
}
