package com.github.chen0040.gp.lgp.gp;


import com.github.chen0040.gp.lgp.enums.LGPCrossoverType;
import com.github.chen0040.gp.lgp.program.Instruction;
import com.github.chen0040.gp.lgp.program.Program;
import com.github.chen0040.gp.lgp.program.ProgramManager;
import com.github.chen0040.gp.services.RandEngine;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 5/5/2017.
 * Currently the following crossovers are supported:
 *
 * linear
 * one-point
 * one-segment
 */
public class Crossover {

   public static void apply(Program program1, Program program2, ProgramManager manager, RandEngine randEngine) {
      LGPCrossoverType crossoverType = manager.getCrossoverType();
      switch(crossoverType){
         case Linear:
            linearCrossover(program1, program2, manager,randEngine);
            break;
         case OnePoint:
            onePointCrossover(program1, program2, manager, randEngine);
            break;
         case OneSegment:
            oneSegmentCrossover(program1, program2, manager,randEngine);
            break;
         default:
            throw new NotImplementedException();
      }

   }

   /* Xianshun says:
       This operator is derived from Algorithm 5.3 in Section 5.7.3 of Linear Genetic Programming
   */
   /* Xianshun says: (From Section 5.7.3 of Linear Genetic Programming
      Crossover requires, by definition, that information is exchanged between individual programs.
      However, an exchange always includes two operations on an individual, the deletion and
      the insertion of a subprogram. The imperative program representation allows instructions to be
      deleted without replacement since instructon operands, e.g. register pointers, are always defined.
      Instructions may also be inserted at any position without a preceding deletion, at least if the maximum
      program length is not exceeded.

      If we want linear crossover to be less disruptive it may be a good idea to execute only one operation per
      individual. this consideration motivates a one-segment or one-way recombination of linear genetic
      programs as described by Algorithm 5.3.

      Standard linear crossover may also be refered to as two-segment recombinations, in these terms.
   */
   private static void oneSegmentCrossover(Program gp1, Program gp2, ProgramManager manager, RandEngine randEngine) {
      double prob_r = randEngine.uniform();
      if((gp1.length() < manager.getMaxProgramLength()) && ((prob_r <= manager.getInsertionProbability() || gp1.length()==manager.getMinProgramLength())))
      {
         int i1=randEngine.nextInt(gp1.length());
         int max_segment_length=gp2.length() < manager.getMaxSegmentLength() ? gp2.length() : manager.getMaxSegmentLength();
         int ls2=1+randEngine.nextInt(max_segment_length);
         if(gp1.length()+ls2 > manager.getMaxProgramLength())
         {
            ls2=manager.getMaxProgramLength()-gp1.length();
         }
         int i2=randEngine.nextInt(gp2.length()-ls2);

         List<Instruction> instructions1=gp1.getInstructions();
         List<Instruction> instructions2=gp2.getInstructions();

         List<Instruction> s=new ArrayList<>();
         for(int i=i2; i != (i2+ls2); ++i)
         {
            Instruction instruction=instructions2.get(i);
            Instruction instruction_cloned=instruction.makeCopy();
            s.add(instruction_cloned);
         }

         instructions1.addAll(i1, s);
      }

      if((gp1.length() > manager.getMinProgramLength()) && ((prob_r > manager.getInsertionProbability()) || gp1.length() == manager.getMaxProgramLength()))
      {
         int max_segment_length=(gp2.length() < manager.getMaxSegmentLength()) ? gp2.length() : manager.getMaxSegmentLength();
         int ls1=1+randEngine.nextInt(max_segment_length);

         if(gp1.length() < ls1)
         {
            ls1=gp1.length() - manager.getMinProgramLength();
         }
         else if(gp1.length() - ls1 < manager.getMinProgramLength())
         {
            ls1=gp1.length() - manager.getMinProgramLength();
         }
         int i1=randEngine.nextInt(gp1.length()-ls1);
         List<Instruction> instructions1=gp1.getInstructions();

         for(int j = ls1-1; j >= i1; j--) {
            instructions1.remove(j);
         }
      }

      gp1.invalidateCost();
   }

   /* Xianshun says:
       This operator is derived from Algorithm 5.2 in Section 5.7.2 of Linear Genetic Programming
   */
   private static void onePointCrossover(Program gp1, Program gp2, ProgramManager manager, RandEngine randEngine) {
      // Xianshun says:
      // this implementation is derived from Algorithm 5.1 in Section 5.7.1 of Linear
      // Genetic Programming

      // length(gp1) <= length(gp2)
      if (gp1.length() > gp2.length())
      {
         gp1 = gp2;
         gp2 = gp1;
      }

      int max_distance_of_crossover_points = (gp1.length() - 1) < manager.getMaxDistanceOfCrossoverPoints() ? manager.getMaxDistanceOfCrossoverPoints() : (gp1.length() - 1);

      int i1 = randEngine.nextInt(gp1.length());
      int i2 = randEngine.nextInt(gp2.length());

      int crossover_point_distance = (i1 > i2) ? (i1 - i2) : (i2 - i1);

      int ls1 = gp1.length() - i1;
      int ls2 = gp2.length() - i2;

      // 1. assure abs(i1-i2) <= max_distance_of_crossover_points
      // 2. assure l(s1) <= l(s2)
      boolean not_feasible=true;
      while (not_feasible)
      {
         not_feasible = false;
         // ensure that the maximum distance between two crossover points is not exceeded
         if (crossover_point_distance > max_distance_of_crossover_points)
         {
            not_feasible = true;
            i1 = randEngine.nextInt(gp1.length());
            i2 = randEngine.nextInt(gp2.length());
            crossover_point_distance = (i1 > i2) ? (i1 - i2) : (i2 - i1);
         }
         else
         {
            ls1 = gp1.length() - i1;
            ls2 = gp2.length() - i2;
            // assure than l(s1) <= l(s2)
            if (ls1 > ls2)
            {
               not_feasible = true;
               i1 = randEngine.nextInt(gp1.length());
               i2 = randEngine.nextInt(gp2.length());
               crossover_point_distance = (i1 > i2) ? (i1 - i2) : (i2 - i1);
            }
            else
            {
               // assure the length of the program after crossover do not exceed the maximum program length or below minimum program length
               if ((gp2.length() - (ls2 - ls1)) < manager.getMinProgramLength() || (gp1.length() + (ls2 - ls1)) > manager.getMaxProgramLength())
               {
                  not_feasible = true;
                  // when the length constraint is not satisfied, make the segments to be exchanged the same length
                  if (gp1.length() >= gp2.length())
                  {
                     i1 = i2;
                  }
                  else
                  {
                     i2 = i1;
                  }
                  crossover_point_distance = 0;
               }
               else
               {
                  not_feasible = false;
               }
            }
         }

         List<Instruction> instructions1 = gp1.getInstructions();
         List<Instruction> instructions2 = gp2.getInstructions();

         List<Instruction> instructions1_1 = new ArrayList<>();
         List<Instruction> instructions1_2 = new ArrayList<>();

         List<Instruction> instructions2_1 = new ArrayList<>();
         List<Instruction> instructions2_2 = new ArrayList<>();

         for (int i = 0; i < i1; ++i)
         {
            instructions1_1.add(instructions1.get(i));
         }
         for (int i = i1; i < instructions1.size(); ++i)
         {
            instructions1_2.add(instructions1.get(i));
         }

         for (int i = 0; i < i2; ++i)
         {
            instructions2_1.add(instructions2.get(i));
         }
         for (int i = i2; i < instructions2.size(); ++i)
         {
            instructions2_2.add(instructions2.get(i));
         }

         instructions1.clear();
         instructions2.clear();

         for (int i = 0; i < i1; ++i)
         {
            instructions1.add(instructions1_1.get(i));
         }
         for (int i = 0; i < instructions2_2.size(); ++i)
         {
            instructions1.add(instructions2_2.get(i));
         }

         for (int i = 0; i < i2; ++i)
         {
            instructions2.add(instructions2_1.get(i));
         }

         for (int i = 0; i < instructions1_2.size(); ++i)
         {
            instructions2.add(instructions1_2.get(i));
         }

         gp1.invalidateCost();
         gp2.invalidateCost();
      }
   }


   // Xianshun says:
   // this is derived from Algorithm 5.1 of Section 5.7.1 of Linear Genetic Programming
   // this linear crossover can also be considered as two-point crossover
   private static void linearCrossover(Program gp1, Program gp2, ProgramManager manager, RandEngine randEngine) {
      // length(gp1) <= length(gp2)
      if (gp1.length() > gp2.length())
      {
         gp1 = gp2;
         gp2 = gp1;
      }

      // select i1 from gp1 and i2 from gp2 such that abs(i1-i2) <= max_crossover_point_distance
      // max_crossover_point_distance=min{length(gp1) - 1, m_max_distance_of_crossover_points}
      int i1 = randEngine.nextInt(gp1.length());
      int i2 = randEngine.nextInt(gp2.length());
      int cross_point_distance = (i1 > i2) ? (i1 - i2) : (i2 - i1);
      int max_crossover_point_distance = (gp1.length() - 1 > manager.getMaxDistanceOfCrossoverPoints() ? manager.getMaxDistanceOfCrossoverPoints() : gp1.length() - 1);
      while (cross_point_distance > max_crossover_point_distance)
      {
         i1 = randEngine.nextInt(gp1.length());
         i2 = randEngine.nextInt(gp2.length());
         cross_point_distance = (i1 > i2) ? (i1 - i2) : (i2 - i1);
      }

      int s1_max = (gp1.length() - i1) > manager.getMaxDifferenceOfSegmentLength() ? manager.getMaxDifferenceOfSegmentLength() : (gp1.length() - i1);
      int s2_max = (gp2.length() - i2) > manager.getMaxDifferenceOfSegmentLength() ? manager.getMaxDifferenceOfSegmentLength() : (gp2.length() - i2);

      // select s1 from gp1 (start at i1) and s2 from gp2 (start at i2)
      // such that length(s1) <= length(s2)
      // and abs(length(s1) - length(s2)) <= m_max_difference_of_segment_length)
      int ls1 = 1 + randEngine.nextInt(s1_max);
      int ls2 = 1 + randEngine.nextInt(s2_max);
      int lsd = (ls1 > ls2) ? (ls1 - ls2) : (ls2 - ls1);
      while ((ls1 > ls2) && (lsd > manager.getMaxDifferenceOfSegmentLength()))
      {
         ls1 = 1 + randEngine.nextInt(s1_max);
         ls2 = 1 + randEngine.nextInt(s2_max);
         lsd = (ls1 > ls2) ? (ls1 - ls2) : (ls2 - ls1);
      }

      if(((gp2.length() - (ls2-ls1)) < manager.getMinProgramLength() || ((gp1.length()+(ls2-ls1)) > manager.getMaxProgramLength())))
      {
         if(randEngine.uniform() < 0.5)
         {
            ls2=ls1;
         }
         else
         {
            ls1=ls2;
         }
         if((i1+ls1) > gp1.length())
         {
            ls1=ls2=gp1.length()-1;
         }
      }

      List<Instruction> instructions1=gp1.getInstructions();
      List<Instruction> instructions2=gp2.getInstructions();

      List<Instruction> instructions1_1=new ArrayList<>();
      List<Instruction> instructions1_2=new ArrayList<>();
      List<Instruction> instructions1_3=new ArrayList<>();

      List<Instruction> instructions2_1=new ArrayList<>();
      List<Instruction> instructions2_2=new ArrayList<>();
      List<Instruction> instructions2_3=new ArrayList<>();

      for(int i=0; i < i1; ++i)
      {
         instructions1_1.add(instructions1.get(i));
      }
      for(int i=i1; i < i1+ls1; ++i)
      {
         instructions1_2.add(instructions1.get(i));
      }
      for(int i=i1+ls1; i < instructions1.size(); ++i)
      {
         instructions1_3.add(instructions1.get(i));
      }

      for(int i=0; i < i2; ++i)
      {
         instructions2_1.add(instructions2.get(i));
      }
      for(int i=i2; i < i2+ls2; ++i)
      {
         instructions2_2.add(instructions2.get(i));
      }
      for(int i=i2+ls2; i < instructions2.size(); ++i)
      {
         instructions2_3.add(instructions2.get(i));
      }

      instructions1.clear();
      instructions2.clear();

      for(int i=0; i < i1; ++i)
      {
         instructions1.add(instructions1_1.get(i));
      }
      for(int i=0; i < ls2; ++i)
      {
         instructions1.add(instructions2_2.get(i));
      }
      for(int i=0; i < instructions1_3.size(); ++i)
      {
         instructions1.add(instructions1_3.get(i));
      }

      for(int i=0; i < i2; ++i)
      {
         instructions2.add(instructions2_1.get(i));
      }
      for(int i=0; i < ls1; ++i)
      {
         instructions2.add(instructions1_2.get(i));
      }
      for(int i=0; i < instructions2_3.size(); ++i)
      {
         instructions2.add(instructions2_3.get(i));
      }

      gp1.invalidateCost();
      gp2.invalidateCost();
   }
}
