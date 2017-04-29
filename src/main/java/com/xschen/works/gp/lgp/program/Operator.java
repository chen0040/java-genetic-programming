package com.xschen.works.gp.lgp.program;


import com.xschen.works.gp.lgp.enums.OperatorExecutionStatus;

import java.io.Serializable;


/**
 * Created by xschen on 29/4/2017.
 */
public abstract class Operator implements Serializable, Indexable<Operator> {
   private static final long serialVersionUID = 4208195268877995533L;

   protected String symbol;
   protected boolean conditionalConstruct =false;
   protected int index;


   @Override public int getIndex() {
      return index;
   }


   @Override public void setIndex(int index) {
      this.index = index;
   }


   public Operator(String symbol)
   {
      this.symbol = symbol;
   }

   public abstract Operator makeCopy();

   public Operator copy(Operator rhs)
   {
      index = rhs.index;
      conditionalConstruct = rhs.conditionalConstruct;
      symbol = rhs.symbol;
      return this;
   }

   public abstract OperatorExecutionStatus execute(Register operand1, Register operand2, Register destination_register);

   @Override
   public String toString()
   {
      return symbol;
   }


   @Override public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || !(o instanceof Operator))
         return false;

      Operator operator = (Operator) o;

      if (conditionalConstruct != operator.conditionalConstruct) {
         return false;
      }
      if (index != operator.index) {
         return false;
      }
      return symbol != null ? symbol.equals(operator.symbol) : operator.symbol == null;

   }


   @Override public int hashCode() {
      int result = symbol != null ? symbol.hashCode() : 0;
      result = 31 * result + (conditionalConstruct ? 1 : 0);
      result = 31 * result + index;
      return result;
   }
}
