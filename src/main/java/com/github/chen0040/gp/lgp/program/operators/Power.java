package com.github.chen0040.gp.lgp.program.operators;


import com.github.chen0040.gp.lgp.enums.OperatorExecutionStatus;
import com.github.chen0040.gp.lgp.program.Register;
import com.github.chen0040.gp.lgp.program.Operator;
import com.github.chen0040.gp.lgp.LGP;


/**
 * Created by xschen on 30/4/2017.
 */
public class Power extends Operator {

   private static final long serialVersionUID = -3206913752183532206L;


   public Power(){
      super("^");
   }

   @Override public Operator makeCopy() {
      Power clone = new Power();
      clone.copy(this);
      return clone;
   }


   @Override public OperatorExecutionStatus execute(Register operand1, Register operand2, Register destination_register) {
      double x1 =operand1.getValue();
      double x2 = operand2.getValue();
      if ( Math.abs(x1) < 10)
      {
         destination_register.setValue(Math.pow( Math.abs(x1), x2));
      }
      else
      {
         destination_register.setValue(x1 + x2 + LGP.DEFAULT_UNDEFINED_LOW);
      }

      return OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION;
   }
}
