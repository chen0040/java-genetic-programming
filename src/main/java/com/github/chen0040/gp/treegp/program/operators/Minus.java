package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.treegp.program.Operator;
import com.github.chen0040.gp.treegp.program.Primitive;


/**
 * Created by xschen on 11/5/2017.
 */
public class Minus extends Operator {

   private static final long serialVersionUID = 7096964624199720038L;


   public Minus(){
      super(2, "-");
   }

   @Override public void execute(Observation observation){
      setValue(getInput(0) - getInput(1));
   }


   @Override public Primitive makeCopy() {
      return new Minus().copy(this);
   }
}
