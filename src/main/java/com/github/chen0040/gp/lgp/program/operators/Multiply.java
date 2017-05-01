package com.github.chen0040.gp.lgp.program.operators;


import com.github.chen0040.gp.lgp.program.Register;
import com.github.chen0040.gp.lgp.enums.OperatorExecutionStatus;
import com.github.chen0040.gp.lgp.program.Operator;


/**
 * Created by xschen on 30/4/2017.
 */
public class Multiply extends Operator {

   private static final long serialVersionUID = -3206913752183532206L;


   public Multiply(){
      super("*");
   }

   @Override public Operator makeCopy() {
      Multiply clone = new Multiply();
      clone.copy(this);
      return clone;
   }


   @Override public OperatorExecutionStatus execute(Register operand1, Register operand2, Register destination_register) {
      double x1 = operand1.getValue();
      double x2 = operand2.getValue();
      destination_register.setValue(x1 * x2);

      return OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION;
   }
}
