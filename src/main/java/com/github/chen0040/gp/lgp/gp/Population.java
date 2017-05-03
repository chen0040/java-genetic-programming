package com.github.chen0040.gp.lgp.gp;


import com.github.chen0040.gp.lgp.helpers.ProgramHelper;
import com.github.chen0040.gp.lgp.program.Program;
import com.github.chen0040.gp.lgp.program.ProgramManager;
import com.github.chen0040.gp.services.RandEngine;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;


/**
 * Created by xschen on 3/5/2017.
 */
@Getter
@Setter
public class Population {
   private Optional<Program> globalBestProgram = Optional.empty();
   private List<Program> programs=new ArrayList<>();
   private int currentGeneration = 0;

   public Population(){

   }

   protected void evaluate(ProgramManager manager, List<FitnessCase> cases, BiFunction<Program, List<FitnessCase>, Double> evaluator) {
      for(int i=0; i< programs.size(); ++i) {
         ProgramHelper.evaluateCost(programs.get(i), manager, cases, evaluator);
      }
   }

   public boolean isTerminated(ProgramManager manager) {
      return currentGeneration >= manager.getMaxGeneration();
   }

   /*
   public void evolve()
   {
      int iPopSize=this.mConfig.PopulationSize;
      int program_count=0;
      while(program_count < iPopSize)
      {
         KeyValuePair<LGPProgram, LGPProgram> tournament_winners=new KeyValuePair<LGPProgram, LGPProgram>();
         KeyValuePair<LGPProgram, LGPProgram> tournament_losers=new KeyValuePair<LGPProgram, LGPProgram>();
         mReproductionSelectionInstructionFactory.Select(this, ref tournament_winners, ref tournament_losers);

         LGPProgram tp1=tournament_winners.Key.Clone();
         LGPProgram tp2=tournament_winners.Value.Clone();

         double r=DistributionModel.GetUniform();
         if (r < this.mConfig.CrossoverRate)
         {
            mCrossoverInstructionFactory.Crossover(this, tp1, tp2);
         }

         r = DistributionModel.GetUniform();
         if (r < this.mConfig.MacroMutationRate)
         {
            mMutationInstructionFactory.Mutate(this, tp1);
         }

         r = DistributionModel.GetUniform();
         if (r < this.mConfig.MacroMutationRate)
         {
            mMutationInstructionFactory.Mutate(this, tp2);
         }

         r=DistributionModel.GetUniform();
         if(r < this.mConfig.MicroMutationRate)
         {
            tp1.MicroMutate(mGaussian);
         }

         r=DistributionModel.GetUniform();
         if(r < this.mConfig.MicroMutationRate)
         {
            tp2.MicroMutate(mGaussian);
         }

         if(! tp1.IsFitnessValid)
         {
            tp1.EvaluateFitness();
         }
         if(! tp2.IsFitnessValid)
         {
            tp2.EvaluateFitness();
         }

         if(tp1.IsBetterThan(tp2))
         {
            if(tp1.IsBetterThan(mGlobalBestProgram))
            {
               mGlobalBestProgram=tp1.Clone();
            }
         }
         else
         {
            if(tp2.IsBetterThan(mGlobalBestProgram))
            {
               mGlobalBestProgram=tp2.Clone();
            }
         }

         LGPProgram loser1=mSurvivalInstructionFactory.Compete(this, tournament_losers.Key, tp1); // this method returns the pointer to the loser in the competition for survival;
         LGPProgram loser2=mSurvivalInstructionFactory.Compete(this, tournament_losers.Value, tp2);

         if(loser1==tournament_losers.Key)
         {
            ++program_count;
         }
         if(loser2==tournament_losers.Value)
         {
            ++program_count;
         }
      }



      mCurrentGeneration++;
   }*/
}
