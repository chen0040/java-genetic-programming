package com.github.chen0040.gp.treegp.applications;


import com.github.chen0040.data.utils.TupleTwo;
import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.services.Tutorials;
import com.github.chen0040.gp.treegp.TreeGP;
import com.github.chen0040.gp.treegp.enums.TGPCrossoverStrategy;
import com.github.chen0040.gp.treegp.enums.TGPInitializationStrategy;
import com.github.chen0040.gp.treegp.enums.TGPMutationStrategy;
import com.github.chen0040.gp.treegp.enums.TGPPopulationReplacementStrategy;
import com.github.chen0040.gp.treegp.gp.Population;
import com.github.chen0040.gp.treegp.program.Solution;
import com.github.chen0040.gp.treegp.program.operators.*;
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

      List<Observation> data = Tutorials.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      TreeGP tgp = createTreeGP();
      tgp.setDisplayEvery(2);
      Solution program = tgp.fit(trainingData);
      logger.info("global: {}", program.mathExpression());

      test(program, testingData, false);

   }
   
   private void test(Solution program, List<Observation> testingData, boolean silent) {
      for(Observation observation : testingData) {
         program.execute(observation);
         double predicted = observation.getPredictedOutput(0);
         double actual = observation.getOutput(0);

         if(!silent) {
            logger.info("predicted: {}\tactual: {}", predicted, actual);
         }
      }
   }
   
   private TreeGP createTreeGP(){
      TreeGP tgp = TreeGP.defaultConfig();
      tgp.setVariableCount(2);
      tgp.setCostEvaluator((program, observations)->{
         double error = 0;
         for(Observation observation : observations){
            program.execute(observation);
            error += Math.pow(observation.getOutput(0) - observation.getPredictedOutput(0), 2.0);
         }


         return error;
      });
      tgp.setPopulationSize(1000);
      tgp.setMaxGeneration(10); // should be 1000 for full evolution
      return tgp;
   }

   @Test
   public void test_symbolic_regression_with_crossover_subtree_no_bias() {

      List<Observation> data = Tutorials.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      TreeGP tgp = createTreeGP();
      tgp.setCrossoverStrategy(TGPCrossoverStrategy.CROSSVOER_SUBTREE_NO_BIAS);

      Solution program = tgp.fit(trainingData);

      test(program, testingData, true);

   }

   @Test
   public void test_symbolic_regression_with_mutation_hoist() {

      List<Observation> data = Tutorials.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      TreeGP tgp = createTreeGP();
      tgp.setMutationStrategy(TGPMutationStrategy.MUTATION_HOIST);

      Solution program = tgp.fit(trainingData);

      test(program, testingData, true);
   }

   @Test
   public void test_symbolic_regression_with_mutation_subtree_kinnear() {

      List<Observation> data = Tutorials.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      TreeGP tgp = createTreeGP();
      tgp.setMutationStrategy(TGPMutationStrategy.MUTATION_SUBTREE_KINNEAR);

      Solution program = tgp.fit(trainingData);

      test(program, testingData, true);

   }

   @Test
   public void test_symbolic_regression_replacement_mu_plus_lambda() {

      List<Observation> data = Tutorials.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      TreeGP tgp = createTreeGP();
      tgp.setReplacementStrategy(TGPPopulationReplacementStrategy.MuPlusLambda);

      Solution program = tgp.fit(trainingData);

      test(program, testingData, true);

   }




   @Test
   public void test_symbolic_regression_pop_init_ptc_1() {

      List<Observation> data = Tutorials.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      TreeGP tgp = createTreeGP();
      tgp.setPopulationInitializationStrategy(TGPInitializationStrategy.INITIALIZATION_METHOD_PTC1);

      Solution program = tgp.fit(trainingData);

      test(program, testingData, true);

   }
}
