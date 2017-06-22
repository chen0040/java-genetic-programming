package com.github.chen0040.gp.lgp.program.operators;


import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.lgp.enums.OperatorExecutionStatus;
import com.github.chen0040.gp.lgp.program.Operator;
import com.github.chen0040.gp.lgp.program.Register;


/**
 * Created by xschen on 30/4/2017.
 */
public class Plus extends Operator {

   private static final long serialVersionUID = -4785627391093826032L;


   public Plus(){
      super("+");
   }

   @Override public Operator makeCopy() {
      Plus clone = new Plus();
      clone.copy(this);
      return clone;
   }


   @Override public OperatorExecutionStatus execute(Register operand1, Register operand2, Register destination_register, Observation observation) {
      double x1 = operand1.getValue();
      double x2 = operand2.getValue();
      destination_register.setValue(x1 + x2);

      return OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION;
   }
}
