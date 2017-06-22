package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.treegp.program.Operator;
import com.github.chen0040.gp.treegp.program.Primitive;


/**
 * Created by xschen on 11/5/2017.
 */
public class Exp extends Operator {

   private static final long serialVersionUID = 1991415855344953246L;


   public Exp(){
      super(1, "exp");
   }

   @Override public void execute(Observation observation){
      setValue(Math.exp(getInput(0)));
   }


   @Override public Primitive makeCopy() {
      return new Exp().copy(this);
   }
}
