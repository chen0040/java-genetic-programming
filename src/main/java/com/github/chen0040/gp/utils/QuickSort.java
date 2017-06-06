package com.github.chen0040.gp.utils;


import java.util.Comparator;
import java.util.List;


/**
 * Created by xschen on 17/5/2017.
 * Provide my own quick sort instead of java's list sort which becomes unstable when comparing tp solutions
 */
public class QuickSort {
   public static <T> void sort(List<T> a, Comparator<T> compare){
      int lo = 0;
      int hi = a.size()-1;
      sort(a, lo, hi, compare);
   }

   private static <T> void sort(List<T> a, int lo, int hi, Comparator<T> comparator){
      if(lo >= hi) return;

      if(hi - lo < 7){
         insertionSort(a, lo, hi, comparator);
         return;
      }

      int j = partition(a, lo, hi, comparator);
      sort(a, lo, j-1, comparator);
      sort(a, j+1, hi, comparator);
   }

   private static <T> void insertionSort(List<T> a, int lo, int hi, Comparator<T> comparator){
      for(int i=lo+1; i <= hi; ++i){
         for(int j=i-1; j >= lo; --j){
            if(comparator.compare(a.get(j+1), a.get(j)) < 0){
               CollectionUtils.exchange(a, j, j+1);
            } else {
               break;
            }
         }
      }
   }

   private static <T> int partition(List<T> a, int lo, int hi, Comparator<T> comparator) {
      int i, j;
      T v = a.get(lo);
      i = lo;
      j = hi+1;
      while(true){
         while(comparator.compare(a.get(++i), v) < 0){
            if(i >= hi) break;
         }
         while(comparator.compare(v, a.get(--j)) < 0) {
            if(j <= lo) break;
         }

         if(i >= j) {
            break;
         }

         CollectionUtils.exchange(a, i, j);
      }

      CollectionUtils.exchange(a, lo, j);
      return j;
   }
}
