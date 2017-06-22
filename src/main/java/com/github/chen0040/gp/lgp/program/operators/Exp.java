package com.github.chen0040.gp.lgp.program.operators;


import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.lgp.enums.OperatorExecutionStatus;
import com.github.chen0040.gp.lgp.program.Operator;
import com.github.chen0040.gp.lgp.program.Register;


/**
 * Created by xschen on 30/4/2017.
 */
public class Exp extends Operator {

   private static final long serialVersionUID = 2378893425680780257L;


   public Exp(){
      super("exp");
   }

   @Override public Operator makeCopy() {
      Exp clone = new Exp();
      clone.copy(this);
      return clone;
   }


   @Override public OperatorExecutionStatus execute(Register operand1, Register operand2, Register destination_register, Observation observation) {
      double x = operand1.getValue();
      destination_register.setValue(Math.exp(x));
      return OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION;
   }
}
