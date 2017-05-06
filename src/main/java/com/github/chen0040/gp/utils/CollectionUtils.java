package com.github.chen0040.gp.utils;


import com.github.chen0040.gp.lgp.program.Program;
import com.github.chen0040.gp.services.RandEngine;

import java.util.Comparator;
import java.util.List;


/**
 * Created by xschen on 4/5/2017.
 */
public class CollectionUtils {
   public static <T> void shuffle(List<T> a, RandEngine randEngine) {
      for(int i=0; i < a.size(); ++i) {
         int j = randEngine.nextInt(i+1);
         exchange(a, i, j);
      }
   }

   public static <T> void exchange(List<T> a, int i, int j) {
      T temp = a.get(i);
      a.set(i, a.get(j));
      a.set(j, temp);
   }


   public static <T extends Comparable<T>> boolean isBetterThan(T a, T b) {
      return a.compareTo(b) < 0;
   }

}
