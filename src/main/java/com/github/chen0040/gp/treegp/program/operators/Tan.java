package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.treegp.program.Operator;


/**
 * Created by xschen on 11/5/2017.
 */
public class Tan extends Operator {

   private static final long serialVersionUID = 315384748598262406L;


   public Tan(){
      super(1, "tan");
   }

   @Override public void execute(Object... tags) {
      setValue(Math.tan(getInput(0)));
   }


   @Override public Operator makeCopy() {
      return new Tan();
   }
}
