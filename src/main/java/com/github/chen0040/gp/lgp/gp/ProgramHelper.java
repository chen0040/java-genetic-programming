package com.github.chen0040.gp.lgp.gp;


import com.github.chen0040.gp.lgp.program.*;
import com.github.chen0040.gp.services.RandEngine;

import java.util.List;
import java.util.function.BiFunction;


/**
 * Created by xschen on 2/5/2017.
 */
public class ProgramHelper {
   public static void initialize(Program program, ProgramManager programManager, RandEngine randEngine, int instructionCount) {

      final int constantCount = programManager.getConstants().size();
      for(int i=0; i < constantCount; ++i){
         program.getConstantSet().add(programManager.constant(i), programManager.constantWeight(i));
      }

      final int registerCount = programManager.getIoRegisterCount();
      for(int i=0; i < registerCount; ++i) {
         program.getRegisterSet().add(new Register(), 1.0);
      }

      final int operatorCount = programManager.getOperatorSet().size();
      for(int i=0; i < operatorCount; ++i) {
         Operator operator = programManager.getOperatorSet().get(i);
         program.getOperatorSet().add(operator, programManager.getOperatorSet().getWeight(i));
      }

      for(int i=0; i < instructionCount; ++i) {
         Instruction instruction = new Instruction();

         // In this method, the instruction created is not guaranteed to be structurally effective
         InstructionHelper.initialize(instruction, program, randEngine);
         program.getInstructions().add(instruction);
      }


   }

   public static double evaluateCost(Program program, ProgramManager manager, List<FitnessCase> cases, BiFunction<Program, List<FitnessCase>, Double> evaluator) {
      program.markStructuralIntrons(manager);

      if(evaluator != null){
         return evaluator.apply(program.makeCopy(), cases);
      } else {
         throw new RuntimeException("Cost evaluator for the linear program is not specified!");
      }
   }
}
