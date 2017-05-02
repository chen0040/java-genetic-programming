package com.github.chen0040.gp.lgp.helpers;


import com.github.chen0040.gp.lgp.program.*;
import com.github.chen0040.gp.services.RandEngine;

import java.util.List;


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

   public static void initialize(Instruction instruction, Program program, RandEngine randEngine) {
      instruction.setOperator(program.getOperatorSet().any(randEngine));

      double p_const = 0.5;
      double r = randEngine.uniform();
      if( r < p_const) {
         instruction.setOperand1(program.getConstantSet().any(randEngine));
      } else {
         instruction.setOperand1(program.getRegisterSet().any(randEngine));
      }

      if(instruction.getOperand1().isConstant()){
         instruction.setOperand2(program.getRegisterSet().any(randEngine));
      } else {
         r = randEngine.uniform();

         if(r < p_const) {
            instruction.setOperand2(program.getConstantSet().any(randEngine));
         } else {
            instruction.setOperand2(program.getRegisterSet().any(randEngine));
         }
      }
      instruction.setTargetOperand(program.getRegisterSet().any(randEngine));
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


   public static Instruction makeCopy(Instruction that, RegisterSet registerSet, ConstantSet constantSet, OperatorSet operatorSet) {

      Instruction clone = new Instruction();
      if(that.getOperand1().isConstant()) {
         clone.setOperand1(constantSet.get(that.getOperand1().getIndex()));
      } else {
         clone.setOperand1(registerSet.get(that.getOperand1().getIndex()));
      }

      if(that.getOperand2().isConstant()) {
         clone.setOperand2(constantSet.get(that.getOperand2().getIndex()));
      } else {
         clone.setOperand2(registerSet.get(that.getOperand2().getIndex()));
      }

      clone.setOperator(operatorSet.get(that.getOperator().getIndex()));
      clone.setTargetOperand(registerSet.get(that.getTargetOperand().getIndex()));

      return clone;
   }
}
