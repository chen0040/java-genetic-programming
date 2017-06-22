package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.treegp.program.Operator;
import com.github.chen0040.gp.treegp.program.Primitive;


/**
 * Created by xschen on 11/5/2017.
 */
public class Sin extends Operator {

   private static final long serialVersionUID = 2052005753552941342L;


   public Sin(){
      super(1, "sin");
   }

   @Override public void execute(Observation observation){
      setValue(Math.sin(getInput(0)));
   }


   @Override public Primitive makeCopy() {
      return new Sin().copy(this);
   }
}
