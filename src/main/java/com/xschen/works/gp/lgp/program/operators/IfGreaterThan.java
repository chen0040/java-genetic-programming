package com.xschen.works.gp.lgp.program.operators;


import com.xschen.works.gp.lgp.enums.OperatorExecutionStatus;
import com.xschen.works.gp.lgp.program.Operator;
import com.xschen.works.gp.lgp.program.Register;


/**
 * Created by xschen on 29/4/2017.
 */
public class IfGreaterThan extends Operator {
   private static final long serialVersionUID = 2670120736590678065L;


   public IfGreaterThan() {
      super("If>");
      conditionalConstruct = true;
   }


   @Override public Operator makeCopy() {
      return new IfGreaterThan().copy(this);
   }


   @Override public OperatorExecutionStatus execute(Register operand1, Register operand2, Register destination_register) {
      if (operand1.getValue() > operand2.getValue())
      {
         return OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION;
      }
      else
      {
         return OperatorExecutionStatus.LGP_SKIP_NEXT_INSTRUCTION;
      }
   }



}
