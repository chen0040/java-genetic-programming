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
   private TGPInitializationStrategy initializationStrategy;
   private TGPCrossoverStrategy crossoverStrategy;
   private TGPMutationStrategy mutationStrategy;
   private TGPPopulationReplacementStrategy populationReplacementStrategy = TGPPopulationReplacementStrategy.MuPlusLambda;

   private RandEngine randEngine = new SimpleRandEngine();
   private int maxDepthForCrossover;
   private int maxProgramDepth;
   private int maxDepthForCreation;
   private double macroMutationRate;
   private double microMutationRate;
   private double crossoverRate;
   private double reproductionRate;
   private double elitismRatio;

   private int populationSize = 1000;

   private int variableCount;
   private OperatorSet operatorSet;
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


}
