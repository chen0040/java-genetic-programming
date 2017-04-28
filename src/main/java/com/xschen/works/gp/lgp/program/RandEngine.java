package com.xschen.works.gp.lgp.program;


import java.util.Random;


/**
 * Created by xschen on 28/4/2017.
 */
public class RandEngine {
   private Random random = new Random(System.currentTimeMillis());
   public double uniform(){
      return random.nextDouble();
   }
}
