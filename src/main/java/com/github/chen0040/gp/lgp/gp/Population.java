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

   protected void evaluate(ProgramManager manager) {
      for(int i=0; i< programs.size(); ++i) {
         Program p = programs.get(i);
         double cost = manager.evaluateCost(p);
         p.setCost(cost);
         p.setCostValid(true);
         updateGlobal(p);
      }
   }

   public void initialize(ProgramManager manager, RandEngine randEngine){
      PopulationInitialization.initialize(programs, manager, randEngine);
      evaluate(manager);
   }


   private void updateGlobal(Program lgp) {
      if(!globalBestProgram.isPresent() || CollectionUtils.isBetterThan(lgp, globalBestProgram.get()))
      {
         globalBestProgram = Optional.of(lgp.makeCopy());
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
         TupleTwo<Program> tournament_losers = tournament.getLosers();

         Program tp1 = tournament_winners._1().makeCopy();
         Program tp2 = tournament_winners._2().makeCopy();


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

         tp1.setCost(manager.evaluateCost(tp1));
         tp1.setCostValid(true);
         updateGlobal(tp1);

         tp2.setCost(manager.evaluateCost(tp2));
         tp2.setCostValid(true);
         updateGlobal(tp2);

         Program loser1 = Replacement.compete(programs, tournament_losers._1(), tp1, manager, randEngine);
         Program loser2 = Replacement.compete(programs, tournament_losers._2(), tp2, manager, randEngine);

         if(loser1==tournament_losers._1())
         {
            ++program_count;
         }
         if(loser2==tournament_losers._2())
         {
            ++program_count;
         }
      }



      currentGeneration++;
   }
}
