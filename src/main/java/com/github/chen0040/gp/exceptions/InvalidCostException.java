package com.github.chen0040.gp.exceptions;


/**
 * Created by xschen on 4/5/2017.
 */
public class InvalidCostException extends RuntimeException {
   public InvalidCostException(String message){
      super(message);
   }

   public InvalidCostException(){
      super();
   }
}
