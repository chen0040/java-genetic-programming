package com.xschen.works.gp.lgp.program.operators;


import com.xschen.works.gp.lgp.enums.OperatorExecutionStatus;
import com.xschen.works.gp.lgp.program.Operator;
import com.xschen.works.gp.lgp.program.Register;


/**
 * Created by xschen on 29/4/2017.
 */
public class IfLessThan extends Operator {
   private static final long serialVersionUID = 5918299756630052543L;


   public IfLessThan() {
      super("If<");
      conditionalConstruct = true;
   }


   @Override public Operator makeCopy() {
      return new IfLessThan().copy(this);
   }


   @Override public OperatorExecutionStatus execute(Register operand1, Register operand2, Register destination_register) {
      if (operand1.getValue() < operand2.getValue())
      {
         return OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION;
      }
      else
      {
         return OperatorExecutionStatus.LGP_SKIP_NEXT_INSTRUCTION;
      }
   }


}
