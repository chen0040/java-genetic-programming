package com.github.chen0040.gp.utils;


import com.github.chen0040.gp.utils.NumberUtils;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


/**
 * Created by xschen on 8/5/2017.
 */
public class NumberUtilsUnitTest {

   @Test
   public void test_for_valid_number(){
      assertTrue(NumberUtils.isValid(0.1));
      assertTrue(NumberUtils.isValid(Double.MAX_VALUE));
      assertTrue(NumberUtils.isValid(Double.MAX_EXPONENT));
      assertFalse(NumberUtils.isValid(Double.POSITIVE_INFINITY));
      assertFalse(NumberUtils.isValid(Double.NEGATIVE_INFINITY));
      assertFalse(NumberUtils.isValid(Double.NaN));
   }
}
