package com.github.chen0040.gp.treegp.applications;


import com.github.chen0040.gp.commons.BasicObservation;
import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.treegp.TreeGP;
import com.github.chen0040.gp.treegp.program.Operator;
import com.github.chen0040.gp.treegp.program.Primitive;
import com.github.chen0040.gp.treegp.program.Solution;
import com.github.chen0040.gp.treegp.program.operators.*;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 23/6/2017.
 */
public class TextGeneratorUnitTest {

   private static class TextObservation extends BasicObservation {

      public String text;
      public TextObservation(String text) {
         super(0, 1);
         this.text = text;
      }
   }

   private static class Concat extends Operator {

      public Concat(){
         super(2, "concat");
      }

      @Override public Primitive makeCopy() {
         return new Concat();
      }

      @Override public void executeWithText(Observation observation) {
         String input1 = getTextInput(0);
         String input2 = getTextInput(1);

         setValue(input1 + " " + input2);
      }
   }

   public double evaluate(String a1, String a2) {
      int J = 0;
      for(int i=0; i < a1.length(); ++i) {
         for(int j = 0; j < a2.length(); ++j) {
            int k = i + j;
            if(k >= a1.length()) {
               break;
            }
            if(a1.charAt(k) != a2.charAt(j)) {
               J = Math.max(j, J);
               break;
            }
         }
      }
      return J;
   }

   @Test
   public void test_simple(){
      TreeGP tgp = new TreeGP();
      tgp.getOperatorSet().addAll(new Concat());
      tgp.addConstants("Hello", "World", "Hi", "There", "Morning", "Me", "Good", "You", "to", "!");
      tgp.setVariableCount(0);

      tgp.setCostEvaluator((program, observations)->{
         double error = 0;
         for(Observation obs : observations){
            TextObservation observation = (TextObservation)obs;
            program.executeWithText(observation);
            String predicted_text = observation.getPredictedTextOutput(0);
            error += Math.max(0, observation.text.length() - evaluate(observation.text, predicted_text));
            error += (double)(predicted_text.length() - observation.text.length()) / observation.text.length();
         }

         return error;
      });
      tgp.setPopulationSize(1000);
      tgp.setDisplayEvery(2);
      tgp.setMaxGeneration(100); // should be 1000 for full evolution

      List<Observation> trainingData = new ArrayList<>();
      TextObservation target = new TextObservation("Hello World Good Morning to You !");
      trainingData.add(target);

      Solution solution = tgp.fit(trainingData);

      solution.executeWithText(target);

      System.out.println(target.getPredictedTextOutput(0));
   }


}
