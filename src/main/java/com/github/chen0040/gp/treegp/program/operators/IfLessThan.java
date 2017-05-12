package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.treegp.program.Operator;


/**
 * Created by xschen on 11/5/2017.
 */
public class IfLessThan extends Operator {

   private static final long serialVersionUID = 8459445339030451008L;

   public IfLessThan() {
      super(4, "if<");

   }


   @Override
   public void execute(Object... tags)
   {
      setValue(0);
      if (this.getInput(0) < this.getInput(1))
      {
         setValue(this.getInput(2));
      }
      else
      {
         setValue(this.getInput(3));
      }
   }


   @Override
   public Operator makeCopy()
   {
      return new IfLessThan().copy(this);
   }
}
