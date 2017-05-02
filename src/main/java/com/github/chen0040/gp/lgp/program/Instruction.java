package com.github.chen0040.gp.lgp.program;


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
