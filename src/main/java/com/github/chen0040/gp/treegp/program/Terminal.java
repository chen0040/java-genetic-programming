package com.github.chen0040.gp.treegp.program;


import java.io.Serializable;


/**
 * Created by xschen on 10/5/2017.
 */
public class Terminal extends Primitive implements Serializable {
   private static final long serialVersionUID = 8438360593097636018L;

   public Terminal(){
      super();
   }


   @Override public Terminal makeCopy() {
      Terminal clone = new Terminal(getSymbol(), getValue(), isReadOnly());
      clone.copy(this);
      return clone;
   }


   @Override public void execute(Object... tags) {

   }


   public Terminal(String symbol, double value, boolean readonly) {
      super(0, symbol, value, readonly);
   }

   public Terminal(String symbol) {
      super(0, symbol, 0, false);
   }

   @Override
   public String toString(){
      if(isReadOnly()){
         return "" + getValue();
      } else {
         return getName();
      }
   }
}
