package com.github.chen0040.gp.lgp.gp;


/**
 * Created by xschen on 2/5/2017.
 */
public class BasicFitnessCase implements FitnessCase {

   private final double[] inputs;
   private final double[] outputs;

   public BasicFitnessCase(int inputCount, int outputCount) {
      inputs = new double[inputCount];
      outputs = new double[outputCount];
   }

   public void writeInput(int index, double value) {
      inputs[index] = value;
   }

   public double readInput(int index) {
      return inputs[index];
   }

   public void writeOutput(int index, double value) {
      outputs[index] = value;
   }


   public double readOutput(int index) {
      return outputs[index];
   }

   public int inputCount() {
      return inputs.length;
   }

   public int outputCount() {
      return outputs.length;
   }
}
