package com.github.chen0040.gp.utils;


import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;


/**
 * Created by xschen on 6/6/2017.
 */
public class QuickSortUnitTest {

   @Test
   public void test_sort(){
      List<Integer> a = Arrays.asList(100, 200, 1, 2, 3, 4, 5, 7, 8, 6, 10, 9);
      QuickSort.sort(a, Integer::compare);

      System.out.println(a);
      for(int i=1; i < a.size(); ++i) {
         assertThat(a.get(i-1)).isLessThan(a.get(i));
      }
   }

}
