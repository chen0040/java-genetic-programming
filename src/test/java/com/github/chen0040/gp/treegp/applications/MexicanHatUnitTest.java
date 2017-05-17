package com.github.chen0040.gp.treegp.applications;


import com.github.chen0040.data.utils.TupleTwo;
import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.services.ProblemCatalogue;
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

      List<Observation> data = ProblemCatalogue.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      TreeGP tgp = createTreeGP();
      tgp.getObservations().addAll(trainingData);

      Population pop = train(tgp, false);

      Solution program = pop.getGlobalBestSolution();
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
      TreeGP tgp = new TreeGP();
      tgp.getOperatorSet().addAll(new Plus(), new Minus(), new Divide(), new Multiply(), new Power());
      tgp.getOperatorSet().addIfLessThanOperator();
      tgp.addConstants(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
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

   private Population train(TreeGP tgp, boolean silent) {
      long startTime = System.currentTimeMillis();
      Population pop = tgp.newPopulation();
      pop.initialize();
      while (!pop.isTerminated())
      {
         pop.evolve();
         if(!silent) {
            logger.info("Mexican Hat Symbolic Regression Generation: {} (Pop: {}), elapsed: {} seconds", pop.getCurrentGeneration(),
                    pop.size(),
                    (System.currentTimeMillis() - startTime) / 1000);
            logger.info("Global Cost: {}\tCurrent Cost: {}", pop.getGlobalBestSolution().getCost(), pop.getCostInCurrentGeneration());
         }
      }

      return pop;
   }

   @Test
   public void test_symbolic_regression_with_crossover_subtree_no_bias() {

      boolean silent = true;

      List<Observation> data = ProblemCatalogue.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      TreeGP tgp = createTreeGP();
      tgp.getObservations().addAll(trainingData);
      tgp.setCrossoverStrategy(TGPCrossoverStrategy.CROSSVOER_SUBTREE_NO_BIAS);

      Population pop = train(tgp, silent);

      Solution program = pop.getGlobalBestSolution();

      test(program, testingData, silent);

   }

   @Test
   public void test_symbolic_regression_with_mutation_hoist() {

      boolean silent = true;

      List<Observation> data = ProblemCatalogue.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      TreeGP tgp = createTreeGP();
      tgp.getObservations().addAll(trainingData);
      tgp.setMutationStrategy(TGPMutationStrategy.MUTATION_HOIST);

      Population pop = train(tgp, silent);

      Solution program = pop.getGlobalBestSolution();

      test(program, testingData, true);
   }

   @Test
   public void test_symbolic_regression_with_mutation_subtree_kinnear() {

      boolean silent = true;

      List<Observation> data = ProblemCatalogue.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      TreeGP tgp = createTreeGP();
      tgp.getObservations().addAll(trainingData);
      tgp.setMutationStrategy(TGPMutationStrategy.MUTATION_SUBTREE_KINNEAR);

      Population pop = train(tgp, silent);

      Solution program = pop.getGlobalBestSolution();

      test(program, testingData, silent);

   }

   @Test
   public void test_symbolic_regression_replacement_mu_plus_lambda() {

      boolean silent = true;

      List<Observation> data = ProblemCatalogue.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      TreeGP tgp = createTreeGP();
      tgp.getObservations().addAll(trainingData);
      tgp.setReplacementStrategy(TGPPopulationReplacementStrategy.MuPlusLambda);

      Population pop = train(tgp, silent);

      Solution program = pop.getGlobalBestSolution();

      test(program, testingData, silent);

   }




   @Test
   public void test_symbolic_regression_pop_init_ptc_1() {

      boolean silent = true;

      List<Observation> data = ProblemCatalogue.mexican_hat();
      CollectionUtils.shuffle(data);
      TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
      List<Observation> trainingData = split_data._1();
      List<Observation> testingData = split_data._2();

      TreeGP tgp = createTreeGP();
      tgp.getObservations().addAll(trainingData);
      tgp.setPopulationInitializationStrategy(TGPInitializationStrategy.INITIALIZATION_METHOD_PTC1);

      Population pop = train(tgp, silent);

      Solution program = pop.getGlobalBestSolution();

      test(program, testingData, silent);

   }
}
