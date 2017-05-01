package com.github.chen0040.gp.lgp.program.operators;


import com.github.chen0040.gp.lgp.enums.OperatorExecutionStatus;
import com.github.chen0040.gp.lgp.program.Register;
import com.github.chen0040.gp.lgp.program.Operator;


/**
 * Created by xschen on 30/4/2017.
 */
public class Sine extends Operator {

   private static final long serialVersionUID = 612876391355964531L;


   public Sine() {
      super("sin");
   }


   @Override public Operator makeCopy() {
      Sine clone = new Sine();
      clone.copy(this);
      return clone;
   }


   @Override public OperatorExecutionStatus execute(Register operand1, Register operand2, Register destination_register) {
      double x = operand1.getValue();
      destination_register.setValue(Math.sin(x));
      return OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION;
   }
}
