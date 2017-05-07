package com.github.chen0040.gp.lgp.gp;


/**
 * Created by xschen on 2/5/2017.
 */
public class BasicObservation implements Observation {

   private final double[] inputs;
   private final double[] outputs;
   private final double[] expectedOutputs;

   public BasicObservation(int inputCount, int outputCount) {
      inputs = new double[inputCount];
      outputs = new double[outputCount];
      expectedOutputs = new double[outputCount];
   }

   public void setInput(int index, double value) {
      inputs[index] = value;
   }


   @Override public void setExpectedOutput(int index, double value) {
      expectedOutputs[index] = value;
   }


   public double getInput(int index) {
      return inputs[index];
   }

   public void setOutput(int index, double value) {
      outputs[index] = value;
   }


   public double getOutput(int index) {
      return outputs[index];
   }


   @Override public double getExpectedOutput(int index) {
      return expectedOutputs[index];
   }


   public int inputCount() {
      return inputs.length;
   }

   public int outputCount() {
      return outputs.length;
   }
}
