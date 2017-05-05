package com.github.chen0040.gp.lgp.program;


import com.github.chen0040.gp.lgp.enums.LGPCrossoverType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 29/4/2017.
 */
@Getter
@Setter
public class ProgramManager implements Serializable {

   public static final double DEFAULT_UNDEFINED_LOW = 1;

   private static final long serialVersionUID = 5575895345509778505L;
   private boolean useUndefinedLow = true;
   private double regPosInf = 10000000;
   private double regNegInf = -10000000;
   private double undefinedLow = DEFAULT_UNDEFINED_LOW;
   private double undefinedHigh = 1000000;

   // number of registers of a linear program
   private int registerCount;
   private List<Double> constants = new ArrayList<>();
   private List<Double> constantWeights = new ArrayList<>();
   private OperatorSet operatorSet = new OperatorSet();

   // SEC: parameters for micro-mutation
   // BEGIN
   private double microMutateConstantStandardDeviation = 1;
   private double microMutateOperatorRate = 0.5;
   private double microMutateRegisterRate = 0.5;
   private double microMutateConstantRate = 0.5;
   // END

   // SEC: parameters for macro-mutation
   // BEGIN
   private boolean effectiveMutation = false;
   private double macroMutateInsertionRate = 0.5;
   private double macroMutateDeletionRate = 0.5;
   private int macroMutateMaxProgramLength = 100;
   private int macroMutateMinProgramLength = 20;
   // END

   private int maxGeneration = 1000;
   private int populationSize = 1000;

   private double crossoverRate = 0.1;

   private LGPCrossoverType crossoverType = LGPCrossoverType.Linear;

   // SEC: parameters for linear, one-point, one-segment crossover
   // BEGIN
   private int maxProgramLength = 100;
   private int minProgramLength = 20;
   private int maxSegmentLength = 10;
   private int maxDistanceOfCrossoverPoints = 10;
   private int maxDifferenceOfSegmentLength = 5;
   private double insertionProbability = 0.5;
   // END

   private double macroMutationRate = 0.75;
   private double microMutationRate = 0.25;

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


}
