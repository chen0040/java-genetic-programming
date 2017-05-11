package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.treegp.program.Operator;


/**
 * Created by xschen on 11/5/2017.
 */
public class Cos extends Operator {
   private static final long serialVersionUID = 7801446096548105881L;

   public Cos(){
      super(1, "cos");
   }

   @Override public void execute(Object... tags) {
      setValue(Math.cos(getInput(0)));
   }


   @Override public Operator makeCopy() {
      return new Cos();
   }
}
