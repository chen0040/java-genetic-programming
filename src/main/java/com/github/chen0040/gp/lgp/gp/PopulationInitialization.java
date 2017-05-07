package com.github.chen0040.gp.lgp.gp;


import com.github.chen0040.gp.lgp.enums.LGPInitializationStrategy;
import com.github.chen0040.gp.lgp.helpers.InstructionHelper;
import com.github.chen0040.gp.lgp.program.*;
import com.github.chen0040.gp.services.RandEngine;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;


/**
 * Created by xschen on 7/5/2017.
 */
public class PopulationInitialization {
   public static void initialize(List<Program> programs, ProgramManager manager, RandEngine randEngine) {
      if(manager.getProgramInitializationStrategy() == LGPInitializationStrategy.ConstantLength){
         initializeWithConstantLength(programs, manager, randEngine);
      } else if(manager.getProgramInitializationStrategy() == LGPInitializationStrategy.VariableLength) {
         initializeWithVariableLength(programs, manager, randEngine);
      } else {
         throw new NotImplementedException();
      }
   }

   // Xianshun says:
   // specified here is a variable length initialization that selects initial program
   // lengths from a uniform distribution within a specified range of m_iInitialMinProgLength - m_iIinitialMaxProgLength
   // the method is recorded in chapter 7 section 7.6 page 164 of Linear Genetic Programming 2004
   // Xianshun says:
   // the program generated in this way will have program length as small as
   // iMinProgLength and as large as iMaxProgLength
   // the program length is distributed uniformly between iMinProgLength and iMaxProgLength
   private static void initializeWithVariableLength(List<Program> programs, ProgramManager manager, RandEngine randEngine) {
      int popSize = manager.getPopulationSize();

      for(int i=0; i < popSize; ++i) {
         Program lgp= new Program();
         initialize(lgp, manager, randEngine, randEngine.nextInt(manager.getPopInitMinProgramLength(), manager.getPopInitMaxProgramLength()));
         programs.add(lgp);
      }
   }

   // Xianshun says:
   // the program generated in this way will have program length as small as
   // iMinProgLength and as large as iMaxProgLength
   // the program length is distributed uniformly between iMinProgLength and iMaxProgLength
   private static void initializeWithConstantLength(List<Program> programs, ProgramManager manager, RandEngine randEngine) {
      int popSize = manager.getPopulationSize();

      for(int i=0; i<popSize; ++i)
      {
         Program lgp= new Program();
         initialize(lgp, manager, randEngine, manager.getPopInitConstantProgramLength());
         programs.add(lgp);
      }
   }

   private static void initialize(Program program, ProgramManager programManager, RandEngine randEngine, int instructionCount) {

      final int constantCount = programManager.getConstants().size();
      for(int i=0; i < constantCount; ++i){
         program.getConstantSet().add(programManager.constant(i), programManager.constantWeight(i));
      }

      final int registerCount = programManager.getRegisterCount();
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

}
