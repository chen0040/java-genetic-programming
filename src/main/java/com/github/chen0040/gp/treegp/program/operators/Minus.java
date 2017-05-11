package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.treegp.program.Operator;


/**
 * Created by xschen on 11/5/2017.
 */
public class Minus extends Operator {

   private static final long serialVersionUID = 7096964624199720038L;


   public Minus(){
      super(2, "-");
   }

   @Override public void execute(Object... tags) {
      setValue(getInput(0) - getInput(1));
   }


   @Override public Operator makeCopy() {
      return new Minus();
   }
}
