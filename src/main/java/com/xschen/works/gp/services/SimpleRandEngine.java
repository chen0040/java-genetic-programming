package com.xschen.works.gp.services;


import org.apache.commons.math3.distribution.NormalDistribution;

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


   @Override public double normal(double mean, double sd) {
      NormalDistribution normal = new NormalDistribution(mean, sd);
      return normal.sample();
   }
}
