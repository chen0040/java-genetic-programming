package com.github.chen0040.gp.lgp.applications;


import com.github.chen0040.data.utils.TupleTwo;
import com.github.chen0040.gp.lgp.LGP;
import com.github.chen0040.gp.lgp.enums.LGPCrossoverStrategy;
import com.github.chen0040.gp.lgp.enums.LGPInitializationStrategy;
import com.github.chen0040.gp.lgp.enums.LGPReplacementStrategy;
import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.lgp.gp.Population;
import com.github.chen0040.gp.lgp.program.Program;
import com.github.chen0040.gp.services.Tutorials;
import com.github.chen0040.gp.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;


/**
 * Created by xschen on 7/5/2017.
 */
public class MexicanHatUnitTest {

   private static final Logger logger = LoggerFactory.getLogger(MexicanHatUnitTest.class);


   @Test
   public void test_symbolic_regression() {

      boolean silent = false;
      
      List<Observation> data = Tutorials.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.setDisplayEvery(2);

      Program program = lgp.fit(trainingData);
      logger.info("global: {}", program);

      test(program, testingData, silent);

   }
   
   private void test(Program program, List<Observation> testingData, boolean silent) {
      for(Observation observation : testingData) {
         program.execute(observation);
         double predicted = observation.getPredictedOutput(0);
         double actual = observation.getOutput(0);

         if(!silent) {
            logger.info("predicted: {}\tactual: {}", predicted, actual);
         }
      }
   }
   
   private LGP createLGP(){
      LGP lgp = LGP.defaultConfig();
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


   @Test
   public void test_symbolic_regression_with_crossover_onePoint() {

      List<Observation> data = Tutorials.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.setCrossoverStrategy(LGPCrossoverStrategy.OnePoint);

      Program program = lgp.fit(trainingData);

      test(program, testingData, true);

   }

   @Test
   public void test_symbolic_regression_with_crossover_oneSegment() {

      List<Observation> data = Tutorials.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.setCrossoverStrategy(LGPCrossoverStrategy.OneSegment);

      Program program = lgp.fit(trainingData);

      test(program, testingData, true);

   }

   @Test
   public void test_symbolic_regression_replacement_direct_compete() {

      List<Observation> data = Tutorials.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.setReplacementStrategy(LGPReplacementStrategy.DirectCompetition);

      Program program = lgp.fit(trainingData);

      test(program, testingData, true);

   }


   @Test
   public void test_symbolic_regression_effective_mutation() {

      List<Observation> data = Tutorials.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.setEffectiveMutation(true);

      Program program = lgp.fit(trainingData);

      test(program, testingData, true);

   }

   @Test
   public void test_symbolic_regression_pop_init_const_length() {

      List<Observation> data = Tutorials.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      LGP lgp = createLGP();
      lgp.setProgramInitializationStrategy(LGPInitializationStrategy.ConstantLength);

      Program program = lgp.fit(trainingData);

      test(program, testingData, true);

   }
}
