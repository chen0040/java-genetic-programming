package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.treegp.program.Operator;


/**
 * Created by xschen on 11/5/2017.
 */
public class Sqrt extends Operator {

   private static final long serialVersionUID = 4747765626248896515L;


   public Sqrt(){
      super(1, "sqrt");
   }

   @Override public void execute(Object... tags) {
      setValue(Math.sqrt(getInput(0)));
   }


   @Override public Operator makeCopy() {
      return new Sqrt();
   }
}
