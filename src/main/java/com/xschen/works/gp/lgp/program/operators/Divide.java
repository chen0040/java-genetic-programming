package com.xschen.works.gp.lgp.program.operators;

import com.xschen.works.gp.lgp.enums.OperatorExecutionStatus;
import com.xschen.works.gp.lgp.program.Operator;
import com.xschen.works.gp.lgp.program.ProgramManager;
import com.xschen.works.gp.lgp.program.Register;


/**
 * Created by xschen on 30/4/2017.
 */
public class Divide extends Operator {

   private static final long serialVersionUID = 5146577862092367213L;
   private double undefined;

   public Divide(double undefined){
      super("/");
      this.undefined = undefined;
   }

   public Divide(){
      super("/");
      this.undefined = ProgramManager.DEFAULT_UNDEFINED_LOW;
   }

   @Override public Operator makeCopy() {
      Divide clone = new Divide(undefined);
      clone.copy(this);
      return clone;
   }


   @Override public OperatorExecutionStatus execute(Register operand1, Register operand2, Register destination_register) {
      double x1 = operand1.getValue();
      double x2 = operand2.getValue();
      if (x2 == 0)
      {
         destination_register.setValue(x1 + undefined);
      }
      else
      {
         destination_register.setValue(x1 / x2);
      }

      return OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION;
   }
}
