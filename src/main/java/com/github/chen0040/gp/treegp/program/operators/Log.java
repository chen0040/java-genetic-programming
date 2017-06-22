package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.treegp.program.Operator;
import com.github.chen0040.gp.treegp.program.Primitive;


/**
 * Created by xschen on 11/5/2017.
 */
public class Log extends Operator {

   private static final long serialVersionUID = 5304275773103423717L;


   public Log(){
      super(1, "log");
   }

   @Override public void execute(Observation observation){
      setValue(Math.log(getInput(0)));
   }


   @Override public Primitive makeCopy() {
      return new Log().copy(this);
   }
}
