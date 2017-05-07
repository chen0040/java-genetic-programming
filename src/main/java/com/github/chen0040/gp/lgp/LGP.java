package com.github.chen0040.gp.lgp;


import com.github.chen0040.gp.lgp.enums.LGPCrossoverStrategy;
import com.github.chen0040.gp.lgp.enums.LGPInitializationStrategy;
import com.github.chen0040.gp.lgp.enums.LGPReplacementStrategy;
import com.github.chen0040.gp.lgp.gp.Observation;
import com.github.chen0040.gp.lgp.gp.Population;
import com.github.chen0040.gp.lgp.program.OperatorSet;
import com.github.chen0040.gp.lgp.program.Program;
import com.github.chen0040.gp.services.RandEngine;
import com.github.chen0040.gp.services.SimpleRandEngine;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;


/**
 * Created by xschen on 29/4/2017.
 */
@Getter
@Setter
public class LGP implements Serializable {

   private static final long serialVersionUID = 5575895345509778505L;

   public static final double DEFAULT_UNDEFINED_LOW = 1;
   private boolean useUndefinedLow = true;
   private double regPosInf = 10000000;
   private double regNegInf = -10000000;
   private double undefinedLow = DEFAULT_UNDEFINED_LOW;
   private double undefinedHigh = 1000000;
   private RandEngine randEngine = new SimpleRandEngine();

   // number of registers of a linear program
   private int registerCount;
   @Setter(AccessLevel.NONE)
   private List<Double> constants = new ArrayList<>();
   @Setter(AccessLevel.NONE)
   private List<Double> constantWeights = new ArrayList<>();
   private OperatorSet operatorSet = new OperatorSet();

   private int maxGeneration = 1000;
   private int populationSize = 1000;

   // SEC: parameters for population initialization
   // BEGIN
   private LGPInitializationStrategy programInitializationStrategy = LGPInitializationStrategy.VariableLength;
   private int popInitConstantProgramLength = 10;
   private int popInitMaxProgramLength = 15;
   private int popInitMinProgramLength = 5;
   // END

   // SEC: parameters for crossover
   // BEGIN
   private double crossoverRate = 0.1;
   private LGPCrossoverStrategy crossoverStrategy = LGPCrossoverStrategy.Linear;
   private int maxProgramLength = 100;
   private int minProgramLength = 20;
   private int maxSegmentLength = 10;
   private int maxDistanceOfCrossoverPoints = 10;
   private int maxDifferenceOfSegmentLength = 5;
   private double insertionProbability = 0.5;
   // END

   // SEC: parameters for micro-mutation
   // BEGIN
   private double macroMutationRate = 0.75;
   private double microMutateConstantStandardDeviation = 1;
   private double microMutateOperatorRate = 0.5;
   private double microMutateRegisterRate = 0.5;
   private double microMutateConstantRate = 0.5;
   // END

   // SEC: parameters for macro-mutation
   // BEGIN
   private double microMutationRate = 0.25;
   private boolean effectiveMutation = false;
   private double macroMutateInsertionRate = 0.5;
   private double macroMutateDeletionRate = 0.5;
   private int macroMutateMaxProgramLength = 100;
   private int macroMutateMinProgramLength = 20;
   // END

   // SEC: parameters for cost evaluation
   // BEGIN
   private List<Observation> observations = new ArrayList<>();
   private BiFunction<Program, List<Observation>, Double> costEvaluator;
   // END

   // SEC: parameters for replacement
   // BEGIN
   private LGPReplacementStrategy replacementStrategy = LGPReplacementStrategy.ProbabilisticReplacement;
   private double replacementProbability = 1.0;
   // END




   public double undefined(){
      if(useUndefinedLow){
         return undefinedLow;
      }
      return undefinedHigh;
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

   public double evaluateCost(Program program) {
      program.markStructuralIntrons(this);

      if(costEvaluator != null){
         return costEvaluator.apply(program.makeEffectiveCopy(), observations);
      } else {
         throw new RuntimeException("Cost evaluator for the linear program is not specified!");
      }
   }

   public Population newPopulation(){
      return new Population(this, randEngine);
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
