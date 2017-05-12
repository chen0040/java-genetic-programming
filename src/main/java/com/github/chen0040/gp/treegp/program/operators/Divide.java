package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.treegp.program.Operator;


/**
 * Created by xschen on 11/5/2017.
 */
public class Divide extends Operator {
   private static final long serialVersionUID = 8365660313698391735L;


   public Divide(){
      super(2, "/");
   }

   @Override public void execute(Object... tags) {
      double x1 = getInput(0);
      double x2 = getInput(1);
      if (Math.abs(x2) < 0.001)
      {
         setValue(x1);
      }
      else
      {
         setValue(x1 / x2);
      }
   }


   @Override public Operator makeCopy() {
      return new Divide().copy(this);
   }
}
