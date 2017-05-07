package com.github.chen0040.gp.lgp.gp;


import com.github.chen0040.gp.lgp.helpers.InstructionHelper;
import com.github.chen0040.gp.lgp.program.Instruction;
import com.github.chen0040.gp.lgp.program.Program;
import com.github.chen0040.gp.lgp.LGP;
import com.github.chen0040.gp.services.RandEngine;

import java.util.List;


/**
 * Created by xschen on 5/5/2017.
 */
public class MicroMutation {
   /// <summary>
   /// this is derived from the micro mutation implementation in section
   /// 6.2.2 of Linear Genetic Programming
   /// 1. randomly select an (effective) instruction with a constant c
   /// 2. change constant c through a standard deviation from the current value
   /// c:=c + normal(mean:=0, standard_deviation)
   /// </summary>
   private static void mutateInstructionConstant(Program program, LGP manager, RandEngine randEngine)
   {
      Instruction selected_instruction = null;
      for (Instruction instruction : program.getInstructions())
      {
         if (!instruction.isStructuralIntron() && (instruction.getOperand1().isConstant() || instruction.getOperand2().isConstant()))
         {
            if (selected_instruction == null)
            {
               selected_instruction = instruction;
            }
            else if (randEngine.uniform() < 0.5)
            {
               selected_instruction = instruction;
            }
         }

      }
      if (selected_instruction != null)
      {
         InstructionHelper.mutateConstant(selected_instruction, randEngine, manager.getMicroMutateConstantStandardDeviation());
      }
   }

   private static void mutateInstructionRegister(Program program, RandEngine randEngine)
   {
      final List<Instruction> instructions = program.getInstructions();
      final int instructionCount = instructions.size();
      Instruction selected_instruction = instructions.get(randEngine.nextInt(instructionCount));
      double p_const = 0;
      for (Instruction instruction : instructions)
      {
         if (instruction.getOperand1().isConstant() || instruction.getOperand2().isConstant())
         {
            p_const += 1.0;
         }
      }
      p_const /= instructionCount;
      InstructionHelper.mutateRegister(program,selected_instruction, randEngine, p_const);
   }

   private static void mutateInstructionOperator(Program program, RandEngine randEngine)
   {
      final List<Instruction> instructions = program.getInstructions();
      final int instructionCount = instructions.size();
      Instruction instruction =instructions.get(randEngine.nextInt(instructionCount));
      InstructionHelper.mutateOperator(program, instruction, randEngine);
   }

   /// <summary>
   /// the micro-mutation is derived from Linear Genetic Programming 2004 chapter 6 section 6.2.2
   /// three type selection probability are first determined and roulette wheel is used to decide which
   /// mutation type is to be performed
   /// 1. if micro-mutate-operator type is selected, then randomly pick an instruction and
   /// randomly select an instruction and mutate its operator to some other operator from the operator set
   /// 2. if micro-mutate-register type is selected, then randomly pick an instruction and
   /// randomly select one of the two operands, then
   /// 2.1 with a constant selection probability p_{const}, a randomly selected constant register is assigned to the selected operand
   /// 2.2 with probability 1-p_{const}, a randomly selected variable register is assigned to the selected operand
   /// p_{const} is the proportion of instruction that holds a constant value.
   /// 3. if micro-mutate-constant type is selected, then randomly pick an effective instruction with a constant as one
   /// of its register value, mutate the constant to c+$N(0, \omega_{\mu}$
   /// </summary>
   public static void mutate(Program program, LGP manager, RandEngine randEngine)
   {
      double micro_mutate_operator_rate = manager.getMicroMutateOperatorRate();
      double micro_mutate_register_rate = manager.getMicroMutateRegisterRate();
      double micro_mutate_constant_rate = manager.getMicroMutateConstantRate();

      double sum = micro_mutate_constant_rate + micro_mutate_operator_rate + micro_mutate_register_rate;

      micro_mutate_register_rate /= sum;
      micro_mutate_operator_rate /= sum;

      double operator_sector = micro_mutate_operator_rate;
      double register_sector = operator_sector + micro_mutate_register_rate;

      double r = randEngine.uniform();
      if (r < operator_sector)
      {
         mutateInstructionOperator(program, randEngine);
      }
      else if (r < register_sector)
      {
         mutateInstructionRegister(program,randEngine);
      }
      else
      {
         mutateInstructionConstant(program, manager, randEngine);
      }

      program.invalidateCost();
   }
}
