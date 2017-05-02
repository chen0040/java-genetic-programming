package com.github.chen0040.gp.lgp.gp;


/**
 * Created by xschen on 2/5/2017.
 */
public interface FitnessCase {

   double readInput(int index);
   void writeOutput(int index, double value);

   int inputCount();
   int outputCount();
}
