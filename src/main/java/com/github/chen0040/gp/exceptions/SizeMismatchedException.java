package com.github.chen0040.gp.exceptions;


/**
 * Created by xschen on 11/5/2017.
 */
public class SizeMismatchedException extends RuntimeException {
   private final int expected;
   private final int actual;
   public SizeMismatchedException(int expected, int actual) {
      super("The actual size " + actual + " is different from expected size " + expected);
      this.expected = expected;
      this.actual = actual;
   }


}
