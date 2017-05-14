package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.treegp.program.Operator;
import com.github.chen0040.gp.treegp.program.Primitive;


/**
 * Created by xschen on 11/5/2017.
 */
public class Modulo extends Operator {
   private static final long serialVersionUID = 8365660313698391735L;


   public Modulo(){
      super(2, "%");
   }

   @Override public void execute(Object... tags) {
      double x1 = getInput(0);
      double x2 = getInput(1);
      if (x1 < 0) x1 = -x1;
      if (x2 < 0) x2 = -x2;

      if (x2 == 0)
      {
         setValue(x1);
      }
      else
      {
         setValue(x1 % x2);
      }
   }


   @Override public Primitive makeCopy() {
      return new Modulo().copy(this);
   }
}
