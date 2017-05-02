package com.github.chen0040.gp.lgp.gp;


import com.github.chen0040.gp.lgp.program.Instruction;
import com.github.chen0040.gp.lgp.program.Program;
import com.github.chen0040.gp.lgp.program.Register;
import com.github.chen0040.gp.services.RandEngine;


/**
 * Created by xschen on 2/5/2017.
 */
public class InstructionHelper {
   public static void mutateConstant(Instruction instruction, RandEngine randEngine, double sd){
      if(instruction.getOperand1().isConstant()){
         instruction.getOperand1().mutate(randEngine, sd);
      } else {
         instruction.getOperand2().mutate(randEngine, sd);
      }
   }

   public static void mutateOperator(Program program, Instruction instruction, RandEngine randEngine) {
      instruction.setOperator(program.getOperatorSet().any(randEngine));
   }

   public static void mutateRegister(Program program, Instruction instruction, RandEngine randEngine) {
      mutateRegister(program, instruction, randEngine, 0.5);
   }

   public static void reassign2Program(Instruction instruction, Program program) {
      instruction.setOperator(program.getOperatorSet().get(instruction.getOperator().getIndex()));
      if(instruction.getOperand1().isConstant()){
         instruction.setOperand1(program.getConstantSet().get(instruction.getOperand1().getIndex()));
      } else {
         instruction.setOperand1(program.getRegisterSet().get(instruction.getOperand1().getIndex()));
      }

      if(instruction.getOperand2().isConstant()){
         instruction.setOperand2(program.getConstantSet().get(instruction.getOperand2().getIndex()));
      } else {
         instruction.setOperand2(program.getRegisterSet().get(instruction.getOperand2().getIndex()));
      }
   }

   public static void mutateRegister(Program program, Instruction instruction, RandEngine randEngine, double p_const) {
      double r = randEngine.uniform();
      if (r < 0.5) {
         instruction.setTargetOperand(program.getRegisterSet().anyOther(instruction.getTargetOperand(), randEngine));
      } else {
         r = randEngine.uniform();
         Register arg1, arg2;
         if(r < 0.5){
            arg1 = instruction.getOperand1();
            arg2 = instruction.getOperand2();
         } else {
            arg1 = instruction.getOperand2();
            arg2 = instruction.getOperand1();
         }

         if(arg2.isConstant()){
            arg1 = program.getRegisterSet().any(randEngine);
         } else {
            r = randEngine.uniform();
            if (r < p_const) {
               arg1 = program.getConstantSet().any(randEngine);
            } else {
               arg1 = program.getRegisterSet().any(randEngine);
            }
         }

         instruction.setOperand1(arg1);
         instruction.setOperand2(arg2);
      }
   }
}
