package com.xschen.works.gp.services;


import java.util.Random;


/**
 * Created by xschen on 28/4/2017.
 */
public class SimpleRandEngine implements RandEngine {

   private Random random = new Random(System.currentTimeMillis());

   @Override
   public double uniform(){
      return random.nextDouble();
   }
}
