package com.github.chen0040.gp.services;


import com.github.chen0040.gp.commons.BasicObservation;
import com.github.chen0040.gp.commons.Observation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;


/**
 * Created by xschen on 20/6/2017.
 */
public class Tutorials {
   public static List<Observation> mexican_hat(){
      List<Observation> result = new ArrayList<>();

      BiFunction<Double, Double, Double> mexican_hat_func = (x1, x2) -> (1 - x1 * x1 / 4 - x2 * x2 / 4) * Math.exp(- x1 * x2 / 8 - x2 * x2 / 8);

      double lower_bound=-4;
      double upper_bound=4;
      int period=16;

      double interval=(upper_bound - lower_bound) / period;

      for(int i=0; i<period; i++)
      {
         double x1=lower_bound + interval * i;
         for(int j=0; j<period; j++)
         {
            double x2=lower_bound + interval * j;

            Observation observation = new BasicObservation(2, 1);

            observation.setInput(0, x1);
            observation.setInput(1, x2);
            observation.setOutput(0, mexican_hat_func.apply(x1, x2));

            result.add(observation);
         }
      }

      return result;
   }
}
