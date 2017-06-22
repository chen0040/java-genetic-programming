package com.github.chen0040.gp.lgp.program.operators;


import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.lgp.enums.OperatorExecutionStatus;
import com.github.chen0040.gp.lgp.program.Operator;
import com.github.chen0040.gp.lgp.LGP;
import com.github.chen0040.gp.lgp.program.Register;


/**
 * Created by xschen on 30/4/2017.
 */
public class Log extends Operator {

   private static final long serialVersionUID = -602302230080927334L;

   private double undefined = LGP.DEFAULT_UNDEFINED_LOW;

   public Log(){
      super("log");
   }

   public Log(double undefined){
      super("log");
      this.undefined = undefined;
   }

   @Override public Operator makeCopy() {
      Log clone = new Log();
      clone.copy(this);
      return clone;
   }


   @Override public OperatorExecutionStatus execute(Register operand1, Register operand2, Register destination_register, Observation observation) {
      double x = operand1.getValue();
      if (x == 0)
      {
         destination_register.setValue( x + undefined );
      }
      else
      {
         destination_register.setValue( Math.log(x));
      }

      return OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION;
   }
}
