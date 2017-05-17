package com.github.chen0040.gp.lgp.applications;


import com.github.chen0040.data.utils.TupleTwo;
import com.github.chen0040.gp.lgp.LGP;
import com.github.chen0040.gp.lgp.enums.LGPCrossoverStrategy;
import com.github.chen0040.gp.lgp.enums.LGPInitializationStrategy;
import com.github.chen0040.gp.lgp.enums.LGPReplacementStrategy;
import com.github.chen0040.gp.commons.BasicObservation;
import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.lgp.gp.Population;
import com.github.chen0040.gp.lgp.program.Program;
import com.github.chen0040.gp.lgp.program.operators.*;
import com.github.chen0040.gp.services.ProblemCatalogue;
import com.github.chen0040.gp.utils.CollectionUtils;
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


   @Test
   public void test_symbolic_regression() {

      List<Observation> data = ProblemCatalogue.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.getObservations().addAll(trainingData);

      Population pop = train(lgp, false);

      Program program = pop.getGlobalBestProgram();
      logger.info("global: {}", program);

      test(program, testingData);

   }
   
   private void test(Program program, List<Observation> testingData) {
      for(Observation observation : testingData) {
         program.execute(observation);
         double predicted = observation.getPredictedOutput(0);
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
            error += Math.pow(observation.getOutput(0) - observation.getPredictedOutput(0), 2.0);
         }

         return error;
      });
      lgp.setMaxGeneration(30); // should be 1000 for full evolution
      return lgp;
   }

   private Population train(LGP lgp, boolean slient) {
      long startTime = System.currentTimeMillis();
      Population pop = lgp.newPopulation();
      pop.initialize();
      while (!pop.isTerminated())
      {
         pop.evolve();
         if(!slient) {
            logger.info("Mexican Hat Symbolic Regression Generation: {} (Pop: {}), elapsed: {} seconds", pop.getCurrentGeneration(),
                    pop.size(),
                    (System.currentTimeMillis() - startTime) / 1000);
            logger.info("Global Cost: {}\tCurrent Cost: {}", pop.getGlobalBestProgram().getCost(), pop.getCostInCurrentGeneration());
         }
      }

      return pop;
   }

   @Test
   public void test_symbolic_regression_with_crossover_onePoint() {

      List<Observation> data = ProblemCatalogue.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.getObservations().addAll(trainingData);
      lgp.setCrossoverStrategy(LGPCrossoverStrategy.OnePoint);

      Population pop = train(lgp, true);

      Program program = pop.getGlobalBestProgram();

      test(program, testingData);

   }

   @Test
   public void test_symbolic_regression_with_crossover_oneSegment() {

      List<Observation> data = ProblemCatalogue.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.getObservations().addAll(trainingData);
      lgp.setCrossoverStrategy(LGPCrossoverStrategy.OneSegment);

      Population pop = train(lgp, true);

      Program program = pop.getGlobalBestProgram();

      test(program, testingData);

   }

   @Test
   public void test_symbolic_regression_replacement_direct_compete() {

      List<Observation> data = ProblemCatalogue.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.getObservations().addAll(trainingData);
      lgp.setReplacementStrategy(LGPReplacementStrategy.DirectCompetition);

      Population pop = train(lgp, true);

      Program program = pop.getGlobalBestProgram();

      test(program, testingData);

   }


   @Test
   public void test_symbolic_regression_effective_mutation() {

      List<Observation> data = ProblemCatalogue.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.getObservations().addAll(trainingData);
      lgp.setEffectiveMutation(true);

      Population pop = train(lgp, true);

      Program program = pop.getGlobalBestProgram();

      test(program, testingData);

   }

   @Test
   public void test_symbolic_regression_pop_init_const_length() {

      List<Observation> data = ProblemCatalogue.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.getObservations().addAll(trainingData);
      lgp.setProgramInitializationStrategy(LGPInitializationStrategy.ConstantLength);

      Population pop = train(lgp, true);

      Program program = pop.getGlobalBestProgram();

      test(program, testingData);

   }
}
