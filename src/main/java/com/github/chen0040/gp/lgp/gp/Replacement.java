package com.github.chen0040.gp.lgp.gp;


import com.github.chen0040.gp.lgp.enums.LGPReplacementStrategy;
import com.github.chen0040.gp.lgp.program.Program;
import com.github.chen0040.gp.lgp.LGP;
import com.github.chen0040.gp.services.RandEngine;
import com.github.chen0040.gp.utils.CollectionUtils;

import java.util.List;


/**
 * Created by xschen on 7/5/2017.
 */
public class Replacement {

   // this method returns the pointer to the loser in the competition for survival;
   public static Program compete(List<Program> programs, Program current, Program candidate, LGP manager, RandEngine randEngine) {
      if(manager.getReplacementStrategy() == LGPReplacementStrategy.DirectCompetition) {
         if (CollectionUtils.isBetterThan(candidate, current)) {
            int index = programs.indexOf(current);
            programs.set(index, candidate);
            return current;
         }
         else {
            return candidate;
         }
      } else {
         if(randEngine.uniform() <= manager.getReplacementProbability()){
            int index = programs.indexOf(current);
            programs.set(index, candidate);
            return current;
         } else {
            return candidate;
         }
      }
   }

}
