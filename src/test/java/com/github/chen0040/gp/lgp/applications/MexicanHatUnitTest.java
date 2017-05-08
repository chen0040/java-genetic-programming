package com.github.chen0040.gp.lgp.applications;


import com.github.chen0040.gp.lgp.LGP;
import com.github.chen0040.gp.lgp.enums.LGPCrossoverStrategy;
import com.github.chen0040.gp.lgp.enums.LGPInitializationStrategy;
import com.github.chen0040.gp.lgp.enums.LGPReplacementStrategy;
import com.github.chen0040.gp.lgp.gp.BasicObservation;
import com.github.chen0040.gp.lgp.gp.Observation;
import com.github.chen0040.gp.lgp.gp.Population;
import com.github.chen0040.gp.lgp.program.Program;
import com.github.chen0040.gp.lgp.program.operators.*;
import com.github.chen0040.gp.utils.CollectionUtils;
import com.github.chen0040.gp.utils.TupleTwo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;


/**
 * Created by xschen on 7/5/2017.
 */
public class MexicanHatUnitTest {

   private static final Logger logger = LoggerFactory.getLogger(MexicanHatUnitTest.class);

   private List<Observation> mexican_hat(){
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

   @Test
   public void test_symbolic_regression() {

      List<Observation> data = mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.getObservations().addAll(trainingData);

      Population pop = runGP(lgp);

      Program program = pop.getGlobalBestProgram();
      logger.info("global: {}", program);

      testRunLGP(program, testingData);

   }
   
   private void testRunLGP(Program program, List<Observation> testingData) {
      for(Observation observation : testingData) {
         program.execute(observation);
         double predicted = observation.getExpectedOutput(0);
         double actual = observation.getOutput(0);

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
            error += Math.pow(observation.getOutput(0) - observation.getExpectedOutput(0), 2.0);
         }

         return error;
      });
      lgp.setMaxGeneration(100); // should be 1000 for full evolution
      return lgp;
   }

   private Population runGP(LGP lgp) {
      long startTime = System.currentTimeMillis();
      Population pop = lgp.newPopulation();
      pop.initialize();
      while (!pop.isTerminated())
      {
         pop.evolve();
         logger.info("Mexican Hat Symbolic Regression Generation: {}, elapsed: {} seconds", pop.getCurrentGeneration(), (System.currentTimeMillis() - startTime) / 1000);
         logger.info("Global Cost: {}\tCurrent Cost: {}", pop.getGlobalBestProgram().getCost(), pop.getCostInCurrentGeneration());
      }

      return pop;
   }

   @Test
   public void test_symbolic_regression_with_crossover_onePoint() {

      List<Observation> data = mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.getObservations().addAll(trainingData);
      lgp.setCrossoverStrategy(LGPCrossoverStrategy.OnePoint);

      Population pop = runGP(lgp);

      Program program = pop.getGlobalBestProgram();
      logger.info("global:\n{}", program);

      testRunLGP(program, testingData);

   }

   @Test
   public void test_symbolic_regression_with_crossover_oneSegment() {

      List<Observation> data = mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.getObservations().addAll(trainingData);
      lgp.setCrossoverStrategy(LGPCrossoverStrategy.OneSegment);

      Population pop = runGP(lgp);

      Program program = pop.getGlobalBestProgram();
      logger.info("global: {}", program);

      testRunLGP(program, testingData);

   }

   @Test
   public void test_symbolic_regression_replacement_direct_compete() {

      List<Observation> data = mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.getObservations().addAll(trainingData);
      lgp.setReplacementStrategy(LGPReplacementStrategy.DirectCompetition);

      Population pop = runGP(lgp);

      Program program = pop.getGlobalBestProgram();
      logger.info("global: {}", program);

      testRunLGP(program, testingData);

   }


   @Test
   public void test_symbolic_regression_effective_mutation() {

      List<Observation> data = mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.getObservations().addAll(trainingData);
      lgp.setEffectiveMutation(true);

      Population pop = runGP(lgp);

      Program program = pop.getGlobalBestProgram();
      logger.info("global: {}", program);

      testRunLGP(program, testingData);

   }

   @Test
   public void test_symbolic_regression_pop_init_const_length() {

      List<Observation> data = mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.getObservations().addAll(trainingData);
      lgp.setProgramInitializationStrategy(LGPInitializationStrategy.ConstantLength);

      Population pop = runGP(lgp);

      Program program = pop.getGlobalBestProgram();
      logger.info("global: {}", program);

      testRunLGP(program, testingData);

   }
}
