package com.github.chen0040.gp.lgp.gp;


import com.github.chen0040.gp.lgp.helpers.InstructionHelper;
import com.github.chen0040.gp.lgp.program.Instruction;
import com.github.chen0040.gp.lgp.program.Program;
import com.github.chen0040.gp.lgp.LGP;
import com.github.chen0040.gp.services.RandEngine;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by xschen on 5/5/2017.
 */
public class MacroMutation {

   // Xianshun says:
   // This is derived from Algorithm 6.1 (Section 6.2.1) of Linear Genetic Programming
   // Macro instruction mutations either insert or delete a single instruction.
   // In doing so, they change absolute program length with minimum step size on the
   // level of full instructions, the macro level. On the functional level , a single
   // node is inserted in or deleted from the program graph, together with all
   // its connecting edges.
   // Exchanging an instruction or change the position of an existing instruction is not
   // regarded as macro mutation. Both of these variants are on average more
   // destructive, i.e. they imply a larger variation step size, since they include a deletion
   // and an insertion at the same time. A further, but important argument against
   // substitution of single instructions is that these do not vary program length. If
   // single instruction would only be exchanged there would be no code growth.
   public static void apply(Program child, LGP manager, RandEngine randEngine) {
      double r=randEngine.uniform();
      List<Instruction> instructions=child.getInstructions();
      if(child.length() < manager.getMacroMutateMaxProgramLength() && ((r < manager.getMacroMutateInsertionRate())  || child.length() == manager.getMacroMutateMinProgramLength()))
      {
         Instruction inserted_instruction = new Instruction();
         InstructionHelper.initialize(inserted_instruction, child, randEngine);
         int loc=randEngine.nextInt(child.length());
         if(loc==child.length() - 1)
         {
            instructions.add(inserted_instruction);
         }
         else
         {
            instructions.add(loc, inserted_instruction);
         }

         if(manager.isEffectiveMutation())
         {
            while(loc < instructions.size() && instructions.get(loc).getOperator().isConditionalConstruct())
            {
               loc++;
            }
            if(loc < instructions.size())
            {
               Set<Integer> Reff= new HashSet<>();
               child.markStructuralIntrons(loc, Reff, manager);
               if(Reff.size() > 0)
               {
                  int iRegisterIndex=-1;
                  for(Integer Reff_value : Reff)
                  {
                     if(iRegisterIndex==-1)
                     {
                        iRegisterIndex=Reff_value;
                     }
                     else if(randEngine.uniform() < 0.5)
                     {
                        iRegisterIndex=Reff_value;
                     }
                  }
                  instructions.get(loc).setTargetOperand(child.getRegisterSet().get(iRegisterIndex));
               }
            }
         }
      }
      else if(child.length() > manager.getMacroMutateMinProgramLength() && ((r > manager.getMacroMutateInsertionRate()) || child.length() == manager.getMacroMutateMaxProgramLength()))
      {
         int loc=randEngine.nextInt(instructions.size());
         if(manager.isEffectiveMutation())
         {
            for(int i=0; i<10; i++)
            {
               loc=randEngine.nextInt(instructions.size());
               if(! instructions.get(loc).isStructuralIntron())
               {
                  break;
               }
            }
         }


         instructions.remove(loc);
      }

      child.invalidateCost();
   }
}
