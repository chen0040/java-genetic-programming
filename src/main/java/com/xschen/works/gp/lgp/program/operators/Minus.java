package com.xschen.works.gp.lgp.program.operators;


import com.xschen.works.gp.lgp.enums.OperatorExecutionStatus;
import com.xschen.works.gp.lgp.program.Operator;
import com.xschen.works.gp.lgp.program.Register;


/**
 * Created by xschen on 30/4/2017.
 */
public class Minus extends Operator {

   private static final long serialVersionUID = -4785627391093826032L;


   public Minus(){
      super("-");
   }

   @Override public Operator makeCopy() {
      Minus clone = new Minus();
      clone.copy(this);
      return clone;
   }


   @Override public OperatorExecutionStatus execute(Register operand1, Register operand2, Register destination_register) {
      double x1 = operand1.getValue();
      double x2 = operand2.getValue();
      destination_register.setValue(x1 - x2);

      return OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION;
   }
}
