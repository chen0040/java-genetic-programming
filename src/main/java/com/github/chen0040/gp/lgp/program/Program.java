package com.github.chen0040.gp.lgp.program;


import com.github.chen0040.gp.exceptions.InvalidCostException;
import com.github.chen0040.gp.lgp.LGP;
import com.github.chen0040.gp.lgp.enums.OperatorExecutionStatus;
import com.github.chen0040.gp.commons.BasicObservation;
import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.lgp.helpers.InstructionHelper;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by xschen on 2/5/2017.
 */
@Getter
@Setter
public class Program implements Serializable, Comparable<Program> {
   private static final long serialVersionUID = -9169643464218638898L;
   private RegisterSet registerSet = new RegisterSet();
   private ConstantSet constantSet = new ConstantSet();
   private OperatorSet operatorSet = new OperatorSet();
   private List<Instruction> instructions = new ArrayList<>();

   private double cost;
   private boolean costValid;

   /*
   Source: Brameier, M 2004  On Linear Genetic Programming (thesis)
   
   Algorithm 3.1 (detection of structural introns)
   1. Let set R_eff always contain all registers that are effective at the current program
     position. R_eff := { r | r is output register }.
     Start at the last program instruction and move backwards.
   2. Mark the next preceding operation in program with:
      destination register r_dest element-of R_eff.
     If such an instruction is not found then go to 5.
   3. If the operation directly follows a branch or a sequence of branches then mark these
     instructions too. Otherwise remove r_dest from R_eff .
   4. Insert each source (operand) register r_op of newly marked instructions in R_eff
     if not already contained. Go to 2.
   5. Stop. All unmarked instructions are introns.
   */
   public void markStructuralIntrons(LGP manager) {
    
      int instruction_count=instructions.size();
      for (int i = instruction_count - 1; i >= 0; i--)
      {
         instructions.get(i).setStructuralIntron(true);
      }

      Set<Integer> Reff = new HashSet<>();
      int io_register_count = manager.getRegisterCount();
      for (int i = 0; i < io_register_count; ++i)
      {
         Reff.add(i);
      }

      Instruction current_instruction = null;
      Instruction prev_instruction = null;  // prev_instruction is the last visited instruction from bottom up of the program 

      for (int i = instruction_count - 1; i >= 0; i--)
      {
         prev_instruction = current_instruction;
         current_instruction = instructions.get(i);
         // prev_instruction is not an structural intron and the current_instruction
         // is a conditional construct then, the current_instruction is not structural intron either
         // this directly follows from Step 3 of Algorithm 3.1
         if (current_instruction.getOperator().isConditionalConstruct() && prev_instruction != null)
         {
            if (!prev_instruction.isStructuralIntron())
            {
               current_instruction.setStructuralIntron(false);
            }
         }
         else
         {
            if (Reff.contains(current_instruction.getTargetOperand().getIndex()))
            {
               current_instruction.setStructuralIntron(false);
               Reff.remove(current_instruction.getTargetOperand().getIndex());

               if (!current_instruction.getOperand1().isConstant())
               {
                  Reff.add(current_instruction.getOperand1().getIndex());
               }
               if (!current_instruction.getOperand2().isConstant())
               {
                  Reff.add(current_instruction.getOperand2().getIndex());
               }
            }
         }
      }
   }

   public void markStructuralIntrons(int stop_point, Set<Integer> Reff, LGP manager)
   {
         /*
        Source: Brameier, M 2004  On Linear Genetic Programming (thesis)

        Algorithm 3.1 (detection of structural introns)
        1. Let set R_eff always contain all registers that are effective at the current program
           position. R_eff := { r | r is output register }.
           Start at the last program instruction and move backwards.
        2. Mark the next preceding operation in program with:
            destination register r_dest element-of R_eff.
           If such an instruction is not found then go to 5.
        3. If the operation directly follows a branch or a sequence of branches then mark these
           instructions too. Otherwise remove r_dest from R_eff .
        4. Insert each source (operand) register r_op of newly marked instructions in R_eff
           if not already contained. Go to 2.
        5. Stop. All unmarked instructions are introns.
        */

      // Xianshun says:
      // this is a variant of Algorithm 3.1 that run Algorithm 3.1 until stop_point and return the Reff at that stage

      int instruction_count = instructions.size();
      for (int i = instruction_count - 1; i > stop_point; i--)
      {
         instructions.get(i).setStructuralIntron(true);
      }

      Reff.clear();
      int register_count = manager.getRegisterCount();
      for (int i = 0; i < register_count; ++i)
      {
         Reff.add(i);
      }

      Instruction current_instruction = null;
      Instruction prev_instruction = null;
      for (int i = instruction_count - 1; i > stop_point; i--)
      {
         prev_instruction = current_instruction;
         current_instruction = instructions.get(i);
         // prev_instruction is not an structural intron and the current_instruction
         // is a condictional construct then, the current_instruction is not structural intron either
         // this directly follows from Step 3 of Algorithm 3.1
         if (current_instruction.getOperator().isConditionalConstruct() && prev_instruction != null)
         {
            if (!prev_instruction.isStructuralIntron())
            {
               current_instruction.setStructuralIntron(false);
            }
         }
         else
         {
            if (Reff.contains(current_instruction.getTargetOperand().getIndex()))
            {
               current_instruction.setStructuralIntron(false);
               Reff.remove(current_instruction.getTargetOperand().getIndex());

               if (!current_instruction.getOperand1().isConstant())
               {
                  Reff.add(current_instruction.getOperand1().getIndex());
               }
               if (!current_instruction.getOperand2().isConstant())
               {
                  Reff.add(current_instruction.getOperand2().getIndex());
               }
            }
         }
      }
   }

   public void execute(Observation observation)
   {
      int inputRegisterCount = registerSet.size();
      for(int i=0; i < inputRegisterCount; ++i) {
         registerSet.get(i).setValue(observation.getInput(i % observation.inputCount()));
      }

      OperatorExecutionStatus command = OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION;
      Instruction current_effective_instruction = null;
      Instruction prev_effective_instruction = null;
      for (Instruction instruction : instructions)
      {
         if (instruction.isStructuralIntron())
         {
            continue;
         }
         prev_effective_instruction = current_effective_instruction;
         current_effective_instruction = instruction;
         if (command == OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION)
         {
            command = current_effective_instruction.execute(observation);
            //fitness_case.ReportProgress(instruction.Operator, instruction.Operand1, instruction.Operand2, instruction.DestinationRegister, RegisterSet);
         }
         else
         {
            // Xianshun says:
            // as suggested in Linear Genetic Programming
            // the conditional construct is restricted to single condictional construct
            // an example of single conditional construct would be
            // line 1: if(register[a])
            // line 2: <action1>
            // line 3: <action2>
            // if register[a]==true, then <action1> and <action2> are executed
            // if register[a]==false, then <action1> is skipped and <action2> is executed
            // <action1> and <action2> are restricted to effective instruction
            if (prev_effective_instruction.getOperator().isConditionalConstruct())
            {
               command = OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION;
            }
         }
      }


      int outputRegisterCount = Math.min(registerSet.size(), observation.outputCount());
      for (int i = 0; i < outputRegisterCount; ++i)
      {
         observation.setPredictedOutput(i, registerSet.get(i).getValue());
      }
   }

   public double[] execute(double[] input){
      BasicObservation fitnessCase = new BasicObservation(input.length, input.length);
      execute(fitnessCase);
      double[] output = new double[input.length];
      for(int i=0; i < output.length; ++i) {
         output[i] = fitnessCase.getOutput(i);
      }
      return output;
   }


   public Program makeCopy() {
      Program clone = new Program();
      clone.copy(this, false);
      return clone;
   }

   public void copy(Program that, boolean effectiveOnly) {
      registerSet.copy(that.registerSet);
      constantSet.copy(that.constantSet);
      operatorSet.copy(that.operatorSet);

      instructions.clear();
      for(int i=0; i < that.instructions.size(); ++i) {
         if(effectiveOnly && !that.instructions.get(i).isStructuralIntron()){
            continue;
         }
         instructions.add(InstructionHelper.makeCopy(that.instructions.get(i), registerSet, constantSet, operatorSet));
      }

      cost = that.cost;
      costValid = that.costValid;
   }

   public long effectiveInstructionCount() {
      return instructions.stream().filter(instruction -> !instruction.isStructuralIntron()).count();
   }

   @Override
   public String toString()
   {
      StringBuilder sb = new StringBuilder();
      sb.append("constants:");
      sb.append("\n").append(constantSet);
      sb.append("\nregisters:");
      sb.append("\n").append(registerSet);
      sb.append("\noperators:");
      sb.append("\n").append(operatorSet);
      for (int i = 0; i < instructions.size(); ++i)
      {
         sb.append("instruction[").append(i).append("]: ").append(instructions.get(i)).append("\n");
      }
      if (costValid)
      {
         sb.append("fitness: ").append(cost);
      }
      else
      {
         sb.append("Invalid Fitness");
      }
      return sb.toString();
   }

   public void invalidateCost(){
      costValid = false;
   }


   @Override public int compareTo(Program o) {


      if(!costValid || !o.costValid) {
         throw new InvalidCostException("cost of the programs involved in the comparison is not valid for comparison");
      }

      return Double.compare(cost, o.cost);

   }

   @Override
   public boolean equals(Object obj){
      if(obj == null || !(obj instanceof Program)){
         return false;
      }

      return compareTo((Program)obj) == 0;
   }

   @Override
   public int hashCode(){
      int h = 0;
      for(int i=0; i < instructions.size(); ++i) {
         h = h * 31 + instructions.get(i).hashCode();
      }
      return h;
   }


   public int length() {
      return instructions.size();
   }


   public Program makeEffectiveCopy() {
      Program clone = new Program();
      clone.copy(this, true);
      return clone;
   }
}
