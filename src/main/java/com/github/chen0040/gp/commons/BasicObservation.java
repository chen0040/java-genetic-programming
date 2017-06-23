package com.github.chen0040.gp.commons;


import java.io.Serializable;


/**
 * Created by xschen on 2/5/2017.
 */
public class BasicObservation implements Observation, Serializable {

   private static final long serialVersionUID = 5930617395756548620L;
   private final double[] inputs;
   private final double[] outputs;
   private final double[] expectedOutputs;
   private Serializable tag;

   public BasicObservation(int inputCount, int outputCount) {
      inputs = new double[inputCount];
      outputs = new double[outputCount];
      expectedOutputs = new double[outputCount];
   }

   public void setInput(int index, double value) {
      inputs[index] = value;
   }


   @Override public void setPredictedOutput(int index, double value) {
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


   @Override public double getPredictedOutput(int index) {
      return expectedOutputs[index];
   }


   public int inputCount() {
      return inputs.length;
   }

   public int outputCount() {
      return outputs.length;
   }


   @Override public Serializable getTag() {
      return tag;
   }


   @Override public void setTag(Serializable obj) {
      this.tag = obj;
   }
}