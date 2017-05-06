package com.github.chen0040.gp.lgp.gp;


import com.github.chen0040.gp.lgp.helpers.ProgramHelper;
import com.github.chen0040.gp.lgp.program.Program;
import com.github.chen0040.gp.lgp.program.ProgramManager;
import com.github.chen0040.gp.services.RandEngine;
import com.github.chen0040.gp.utils.CollectionUtils;
import com.github.chen0040.gp.utils.TournamentSelection;
import com.github.chen0040.gp.utils.TournamentSelectionResult;
import com.github.chen0040.gp.utils.TupleTwo;
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
         Program p = programs.get(i);
         double cost = manager.evaluateCost(p);
      }
   }

   public boolean isTerminated(ProgramManager manager) {
      return currentGeneration >= manager.getMaxGeneration();
   }


   public void evolve(ProgramManager manager, RandEngine randEngine)
   {
      int iPopSize = manager.getPopulationSize();
      int program_count=0;
      while(program_count < iPopSize)
      {

         TournamentSelectionResult<Program> tournament = TournamentSelection.select(programs, randEngine);
         TupleTwo<Program> tournament_winners = tournament.getWinners();

         Program tp1=tournament_winners._1().makeCopy();
         Program tp2=tournament_winners._2().makeCopy();


         double r = randEngine.uniform();
         if (r < manager.getCrossoverRate())
         {
            Crossover.apply(tp1, tp2, manager, randEngine);
         }

         r = randEngine.uniform();
         if (r < manager.getMacroMutationRate())
         {
            MacroMutation.mutate(tp1, manager, randEngine);
         }

         r = randEngine.uniform();
         if (r < manager.getMacroMutationRate())
         {
            MacroMutation.mutate(tp2, manager, randEngine);
         }

         r=randEngine.uniform();
         if(r < manager.getMicroMutationRate())
         {
            MicroMutation.mutate(tp1, manager, randEngine);
         }

         r=randEngine.uniform();
         if(r < manager.getMicroMutationRate())
         {
            MicroMutation.mutate(tp2, manager, randEngine);
         }

         if(! tp1.isCostValid())
         {
            tp1.setCost(manager.evaluateCost(tp1));
            tp1.setCostValid(true);
         }
         if(! tp2.isCostValid())
         {
            tp2.setCost(manager.evaluateCost(tp2));
            tp2.setCostValid(true);
         }

         if(CollectionUtils.isBetterThan(tp1, tp2))
         {
            if(!globalBestProgram.isPresent() || CollectionUtils.isBetterThan(tp1, globalBestProgram.get()))
            {
               globalBestProgram = Optional.of(tp1.makeCopy());
            }
         }
         else
         {
            if(!globalBestProgram.isPresent() || CollectionUtils.isBetterThan(tp2, globalBestProgram.get()))
            {
               globalBestProgram= Optional.of(tp2.makeCopy());
            }
         }

         /*
        

         





         LGPProgram loser1=mSurvivalInstructionFactory.Compete(this, tournament_losers.Key, tp1); // this method returns the pointer to the loser in the competition for survival;
         LGPProgram loser2=mSurvivalInstructionFactory.Compete(this, tournament_losers.Value, tp2);

         if(loser1==tournament_losers.Key)
         {
            ++program_count;
         }
         if(loser2==tournament_losers.Value)
         {
            ++program_count;
         }*/
      }



      currentGeneration++;
   }
}
