package com.xschen.works.gp.lgp.program.operators;


import com.xschen.works.gp.lgp.enums.OperatorExecutionStatus;
import com.xschen.works.gp.lgp.program.Operator;
import com.xschen.works.gp.lgp.program.Register;


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


   @Override public OperatorExecutionStatus execute(Register operand1, Register operand2, Register destination_register) {
      double x = operand1.getValue();
      destination_register.setValue(Math.exp(x));
      return OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION;
   }
}
