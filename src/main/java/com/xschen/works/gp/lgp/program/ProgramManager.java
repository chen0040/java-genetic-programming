package com.xschen.works.gp.lgp.program;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * Created by xschen on 29/4/2017.
 */
@Getter
@Setter
public class ProgramManager implements Serializable {

   public static final double DEFAULT_UNDEFINED_LOW = 1;

   private static final long serialVersionUID = 5575895345509778505L;
   private boolean useUndefinedLow = true;
   private double regPosInf = 10000000;
   private double regNegInf = -10000000;
   private double undefinedLow = DEFAULT_UNDEFINED_LOW;
   private double undefinedHigh = 1000000;

   public double undefined(){
      if(useUndefinedLow){
         return undefinedLow;
      }
      return undefinedHigh;
   }
}
