package com.xschen.works.gp.lgp.program;


import com.xschen.works.gp.lgp.enums.OperatorExecutionStatus;

import java.io.Serializable;


/**
 * Created by xschen on 29/4/2017.
 */
public abstract class Operator implements Serializable {
   private static final long serialVersionUID = 4208195268877995533L;

   protected String symbol;
   protected boolean conditionalConstruct =false;
   protected int index;

   public Operator(String symbol)
   {
      this.symbol = symbol;
   }

   public abstract Operator makeCopy();

   public void Copy(Operator rhs)
   {
      index = rhs.index;
      conditionalConstruct = rhs.conditionalConstruct;
      symbol = rhs.symbol;
   }

   public abstract OperatorExecutionStatus Execute(Register operand1, Register operand2, Register destination_register);

   @Override
   public String toString()
   {
      return symbol;
   }
}
