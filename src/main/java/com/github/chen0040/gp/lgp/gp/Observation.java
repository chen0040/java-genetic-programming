package com.github.chen0040.gp.lgp.gp;


/**
 * Created by xschen on 2/5/2017.
 */
public interface Observation {

   double getInput(int index);
   double getOutput(int index);
   double getExpectedOutput(int index);

   void setOutput(int index, double value);
   void setInput(int index, double value);
   void setExpectedOutput(int index, double value);


   int inputCount();
   int outputCount();
}
