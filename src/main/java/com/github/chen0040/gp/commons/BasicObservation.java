package com.github.chen0040.gp.commons;


import java.io.Serializable;


/**
 * Created by xschen on 2/5/2017.
 */
public class BasicObservation implements Observation, Serializable {

   private static final long serialVersionUID = 5930617395756548620L;
   private final double[] inputs;
   private final double[] outputs;
   private final double[] predictedOutputs;
   private final String[] textInputs;
   private final String[] textOutputs;
   private final String[] predictedTextOutputs;
   private Serializable tag;

   public BasicObservation(int inputCount, int outputCount) {
      inputs = new double[inputCount];
      outputs = new double[outputCount];
      predictedOutputs = new double[outputCount];

      textInputs = new String[inputCount];
      textOutputs = new String[outputCount];
      predictedTextOutputs = new String[outputCount];
   }

   public void setInput(int index, double value) {
      inputs[index] = value;
   }


   @Override public void setPredictedOutput(int index, double value) {
      predictedOutputs[index] = value;
   }

   @Override public void setPredictedOutput(int index, String value) { predictedTextOutputs[index] = value; }


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
      return predictedOutputs[index];
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

   @Override public void setOutput(int index, String value) {
      textOutputs[index] = value;
   }
   @Override public void setInput(int index, String value) {
      textInputs[index] = value;
   }

   @Override public String getTextInput(int index) { return textInputs[index]; }
   @Override public String getTextOutput(int index) { return textOutputs[index]; }
   @Override public String getPredictedTextOutput(int index) { return predictedTextOutputs[index]; }
}
