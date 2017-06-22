package com.github.chen0040.gp.lgp.program.operators;


import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.lgp.enums.OperatorExecutionStatus;
import com.github.chen0040.gp.lgp.program.Register;
import com.github.chen0040.gp.lgp.program.Operator;


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


   @Override public OperatorExecutionStatus execute(Register operand1, Register operand2, Register destination_register, Observation observation) {
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
