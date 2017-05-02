package com.github.chen0040.gp.lgp.program;


import com.github.chen0040.gp.lgp.enums.OperatorExecutionStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * Created by xschen on 2/5/2017.
 */
@Getter
@Setter
public class Instruction implements Serializable {
   private static final long serialVersionUID = -2555357716504360141L;

   private Operator operator;
   private Register operand1;
   private Register operand2;
   private Register targetOperand;
   private boolean structuralIntron = false;

   public Instruction(){

   }

   public OperatorExecutionStatus execute()
   {
      return operator.execute(operand1, operand2, targetOperand);
   }


   public Instruction makeCopy()
   {
      Instruction clone = new Instruction();
      clone.structuralIntron = structuralIntron;
      clone.operator = operator;
      clone.operand1 = operand1;
      clone.operand2 = operand2;
      clone.targetOperand = targetOperand;
      return clone;
   }

   @Override
   public String toString() {
      return "<".concat(operator.toString())
              .concat("\t").concat(operand1.toString())
              .concat("\t").concat(operand2.toString())
              .concat("\t").concat(targetOperand.toString())
              .concat(">")
              .concat(structuralIntron ? "(intron)" : "");
   }
}
