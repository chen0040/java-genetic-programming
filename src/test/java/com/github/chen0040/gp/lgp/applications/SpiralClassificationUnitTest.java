package com.github.chen0040.gp.lgp.applications;


import com.github.chen0040.gp.lgp.LGP;
import com.github.chen0040.gp.lgp.enums.LGPCrossoverStrategy;
import com.github.chen0040.gp.lgp.gp.BasicObservation;
import com.github.chen0040.gp.lgp.gp.Observation;
import com.github.chen0040.gp.lgp.gp.Population;
import com.github.chen0040.gp.lgp.program.Program;
import com.github.chen0040.gp.lgp.program.operators.*;
import com.github.chen0040.gp.utils.CollectionUtils;
import com.github.chen0040.gp.utils.FileUtil;
import com.github.chen0040.gp.utils.TupleTwo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 10/5/2017.
 */
public class SpiralClassificationUnitTest {

   private static final Logger logger = LoggerFactory.getLogger(SpiralClassificationUnitTest.class);

   @Test
   public void test_symbolic_classification() throws IOException {
      List<Observation> data = spiral();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.6);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.getObservations().addAll(trainingData);

      Population pop = train(lgp);

      Program program = pop.getGlobalBestProgram();
      logger.info("global: {}", program);

      test(program, testingData);
   }

   private Population train(LGP lgp) {
      long startTime = System.currentTimeMillis();
      Population pop = lgp.newPopulation();
      pop.initialize();
      while (!pop.isTerminated())
      {
         pop.evolve();
         logger.info("Spiral Symbolic Classification Generation: {}, elapsed: {} seconds", pop.getCurrentGeneration(), (System.currentTimeMillis() - startTime) / 1000);
         logger.info("Global Cost: {}\tCurrent Cost: {}", pop.getGlobalBestProgram().getCost(), pop.getCostInCurrentGeneration());
      }

      return pop;
   }

   private void test(Program program, List<Observation> testingData) {
      for(Observation observation : testingData) {
         program.execute(observation);
         int predicted = observation.getPredictedOutput(0) > 0.5 ? -1 : 1;
         int actual = observation.getOutput(0) > 0.5 ? -1 : 1;

         logger.info("predicted: {}\tactual: {}", predicted, actual);
      }
   }

   private LGP createLGP(){
      LGP lgp = new LGP();
      lgp.getOperatorSet().addAll(new Plus(), new Minus(), new Divide(), new Multiply(), new Power());
      lgp.getOperatorSet().addIfLessThanOperator();
      lgp.addConstants(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
      lgp.setRegisterCount(6);
      lgp.setCostEvaluator((program, observations)->{
         double error = 0;
         for(Observation observation : observations){
            program.execute(observation);

            int actual = observation.getOutput(0) > 0.5 ? -1 : 1;
            int predicted = observation.getPredictedOutput(0) > 0.5 ? -1 : 1;
            error += actual != predicted ? 1 : 0;
         }

         return error / observations.size();
      });
      lgp.setMaxGeneration(300); // should be 1000 for full evolution
      lgp.setCrossoverStrategy(LGPCrossoverStrategy.OneSegment);
      lgp.setMaxProgramLength(200);
      lgp.setMinProgramLength(1);

      return lgp;
   }

   private List<Observation> spiral() throws IOException {
      List<Observation> result = new ArrayList<>();

      InputStream inputStream = FileUtil.getResource("spiral-dataset.txt");

      try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
         String line;
         boolean firstLine = true;
         while((line = reader.readLine()) != null){
            if(firstLine){
               firstLine = false;
               continue;
            }

            String[] parts = line.split("\t");
            double x = Double.parseDouble(parts[0]);
            double y = Double.parseDouble(parts[1]);
            int label = Integer.parseInt(parts[2]);

            Observation observation = new BasicObservation(2, 2);

            observation.setInput(0, x);
            observation.setInput(1, y);
            observation.setOutput(0, label == -1 ? 1 : 0);
            observation.setOutput(1, label != -1 ? 1 : 0);

            result.add(observation);

         }
      }catch(IOException ioe){
         logger.error("Failed to read spiral-dataset.txt", ioe);
      }



      return result;
   }
}
