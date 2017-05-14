package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.treegp.program.Operator;
import com.github.chen0040.gp.treegp.program.Primitive;


/**
 * Created by xschen on 11/5/2017.
 */
public class Multiply extends Operator {

   private static final long serialVersionUID = 3475269017944474130L;


   public Multiply(){
      super(2, "*");
   }

   @Override public void execute(Object... tags) {
      double x1 = getInput(0);
      double x2 = getInput(1);
      setValue(x1 * x2);
   }


   @Override public Primitive makeCopy() {
      return new Multiply().copy(this);
   }
}
