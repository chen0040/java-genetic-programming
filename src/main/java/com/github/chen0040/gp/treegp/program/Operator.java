package com.github.chen0040.gp.treegp.program;


import java.io.Serializable;


/**
 * Created by xschen on 10/5/2017.
 * abstract class for Operator
 */
public abstract class Operator extends Primitive<Operator> implements Serializable {
   private static final long serialVersionUID = -8950539267456770919L;

   public Operator(){
      super();
   }

   public Operator(int parameterCount, String symbol){
      super(parameterCount, symbol, 0, false);
   }



   @Override
   public String toString()
   {
      StringBuilder sb=new StringBuilder();
      sb.append(getSymbol());
      sb.append("(");
      for (int i = 0; i < arity(); ++i)
      {
         if(i != 0) {
            sb.append(", ");
         }
         sb.append(getInput(i));
      }
      sb.append(")");
      return sb.toString();
   }


   public abstract void execute(Object... tags);
}
