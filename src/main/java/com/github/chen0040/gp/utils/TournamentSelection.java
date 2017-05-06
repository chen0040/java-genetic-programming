package com.github.chen0040.gp.utils;


import com.github.chen0040.gp.lgp.program.Program;
import com.github.chen0040.gp.services.RandEngine;
import org.apache.commons.math3.exception.OutOfRangeException;

import java.util.List;


/**
 * Created by xschen on 4/5/2017.
 */
public class TournamentSelection {
   public static <T extends Comparable<T>> TournamentSelectionResult<T> select(List<T> population, RandEngine randEngine) {
      if(population.size() < 4) {
         throw new OutOfRangeException(population.size(), 4, 10000000);
      }
      CollectionUtils.shuffle(population, randEngine);

      T good1, good2;
      T bad1, bad2;
      if(CollectionUtils.isBetterThan(population.get(0), population.get(1))){
         good1 = population.get(0);
         bad1 = population.get(1);
      } else {
         good1 = population.get(1);
         bad1 = population.get(0);
      }

      if(CollectionUtils.isBetterThan(population.get(2), population.get(3))){
         good2 = population.get(2);
         bad2 = population.get(3);
      } else {
         good2 = population.get(3);
         bad2 = population.get(2);
      }


      TournamentSelectionResult<T> result = new TournamentSelectionResult<>();

      result.setWinners(new TupleTwo<>(good1, good2));
      result.setLosers(new TupleTwo<>(bad1, bad2));


      return result;

   }
}
