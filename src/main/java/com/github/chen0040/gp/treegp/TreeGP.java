package com.github.chen0040.gp.treegp;


import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.services.RandEngine;
import com.github.chen0040.gp.services.SimpleRandEngine;
import com.github.chen0040.gp.treegp.enums.TGPCrossoverStrategy;
import com.github.chen0040.gp.treegp.enums.TGPInitializationStrategy;
import com.github.chen0040.gp.treegp.enums.TGPMutationStrategy;
import com.github.chen0040.gp.treegp.enums.TGPPopulationReplacementStrategy;
import com.github.chen0040.gp.treegp.gp.Population;
import com.github.chen0040.gp.treegp.program.*;
import com.github.chen0040.gp.treegp.program.operators.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;


/**
 * Created by xschen on 14/5/2017.
 */
@Getter
@Setter
public class TreeGP {
   private TGPInitializationStrategy populationInitializationStrategy = TGPInitializationStrategy.INITIALIZATION_METHOD_RAMPED_GROW;
   private TGPCrossoverStrategy crossoverStrategy = TGPCrossoverStrategy.CROSSOVER_SUBTREE_BIAS;
   private TGPMutationStrategy mutationStrategy = TGPMutationStrategy.MUTATION_SUBTREE;
   private TGPPopulationReplacementStrategy replacementStrategy = TGPPopulationReplacementStrategy.MuPlusLambda;

   private RandEngine randEngine = new SimpleRandEngine();
   private int maxDepthForCrossover = 7;
   private int maxProgramDepth = 7;
   private int maxDepthForCreation =7;
   private double macroMutationRate = 0.25;
   private double microMutationRate = 0.25;
   private double crossoverRate = 0.5;
   private double reproductionRate = 0.0;
   private double elitismRatio;
   private double epsilon = 0.000000001;

   private int populationSize = 1000;
   private int maxGeneration = 1000;

   private int displayEvery = -1;

   private int variableCount;
   private OperatorSet operatorSet = new OperatorSet();
   @Setter(AccessLevel.NONE)
   private List<Double> constants = new ArrayList<>();
   @Setter(AccessLevel.NONE)
   private List<Double> constantWeights = new ArrayList<>();

   // SEC: parameters for cost evaluation
   // BEGIN
   private List<Observation> observations = new ArrayList<>();
   private BiFunction<Solution, List<Observation>, Double> costEvaluator;
   // END

   public int getTreeCountPerSolution(){
      return observations.get(0).outputCount();
   }

   public double constantWeight(int index) {
      if(index >= constantWeights.size()) {
         return 1.0;
      }
      return constantWeights.get(index);
   }

   public double constant(int index) {
      return constants.get(index);
   }

   public double evaluateCost(Solution solution) {
      if(costEvaluator != null){
         return costEvaluator.apply(solution.makeCopy(), observations);
      } else {
         throw new RuntimeException("Cost evaluator for the linear program is not specified!");
      }
   }

   public Population newPopulation(){
      return new Population(this);
   }


   public void addConstant(double constant, double weight) {
      constants.add(constant);
      constantWeights.add(weight);
   }


   public void addConstants(double... constants) {
      for(int i=0; i < constants.length; ++i){
         addConstant(constants[0], 1.0);
      }
   }

   public Solution fit(List<Observation> observations) {
      this.observations.clear();
      this.observations.addAll(observations);

      long startTime = System.currentTimeMillis();
      Population pop = this.newPopulation();
      pop.initialize();
      while (!pop.isTerminated())
      {
         pop.evolve();
         if(displayEvery > 0 && pop.getCurrentGeneration() % displayEvery == 0) {
            long seconds = (System.currentTimeMillis() - startTime) / 1000;
            System.out.println("Generation: " + pop.getCurrentGeneration() + " (Pop: " + pop.size() + "), elapsed: " + seconds + " seconds");
            System.out.println("Global Cost: " + pop.getGlobalBestSolution().getCost() + "\tCurrent Cost: " + pop.getCostInCurrentGeneration());
         }
      }

      return pop.getGlobalBestSolution();
   }

   public static TreeGP defaultConfig(){
      TreeGP tgp = new TreeGP();
      tgp.getOperatorSet().addAll(new Plus(), new Minus(), new Divide(), new Multiply(), new Power());
      tgp.getOperatorSet().addIfLessThanOperator();
      tgp.addConstants(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
      return tgp;
   }


}
