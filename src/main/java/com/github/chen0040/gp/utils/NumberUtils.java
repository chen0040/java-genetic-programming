package com.github.chen0040.gp.utils;


/**
 * Created by xschen on 7/5/2017.
 */
public class NumberUtils {
   public static boolean isValid(double number){
      return Double.isFinite(number) && !Double.isNaN(number);
   }

}
