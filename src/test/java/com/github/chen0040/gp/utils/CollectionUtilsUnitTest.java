package com.github.chen0040.gp.utils;


import com.github.chen0040.gp.exceptions.InvalidCostException;
import com.github.chen0040.gp.lgp.program.Program;
import com.github.chen0040.gp.utils.CollectionUtils;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;


/**
 * Created by xschen on 8/5/2017.
 */
public class CollectionUtilsUnitTest {

   @Test(expectedExceptions = InvalidCostException.class)
   public void test_isBetterThan_invalidCost(){
      Program a = new Program();
      a.setCost(10);

      Program b = new Program();
      b.setCost(20);

      assertTrue(CollectionUtils.isBetterThan(a, b));
   }

   @Test
   public void test_isBetterThan_validCost(){
      Program a = new Program();
      a.setCost(10);
      a.setCostValid(true);

      Program b = new Program();
      b.setCost(20);
      b.setCostValid(true);

      assertTrue(CollectionUtils.isBetterThan(a, b));
   }

   @Test
   public void test_shuffle(){
      List<Double> a = Arrays.asList(10.0, 20.0, 30.0, 40.0);
      CollectionUtils.shuffle(a);
      assertThat(a).size().isEqualTo(4);
      assertThat(a).contains(10.0);
      assertThat(a).contains(20.0);
      assertThat(a).contains(30.0);
      assertThat(a).contains(40.0);
   }

   @Test
   public void test_exchange(){
      List<Double> a = Arrays.asList(10.0, 20.0, 30.0, 40.0);
      CollectionUtils.exchange(a, 0, 1);
      assertThat(a.get(0)).isEqualTo(20.0);
      assertThat(a.get(1)).isEqualTo(10.0);
   }
}
