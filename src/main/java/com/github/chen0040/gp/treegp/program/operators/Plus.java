package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.treegp.program.Operator;


/**
 * Created by xschen on 11/5/2017.
 */
public class Plus extends Operator {

   private static final long serialVersionUID = -5032027501767121684L;


   public Plus(){
      super(2, "+");
   }

   @Override public void execute(Object... tags) {
      double x1 = getInput(0);
      double x2 = getInput(1);
      setValue(x1 + x2);
   }


   @Override public Operator makeCopy() {
      return new Plus().copy(this);
   }
}
