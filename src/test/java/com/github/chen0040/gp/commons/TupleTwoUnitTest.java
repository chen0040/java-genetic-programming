package com.github.chen0040.gp.commons;


import org.testng.annotations.Test;

import static org.testng.Assert.*;


/**
 * Created by xschen on 8/5/2017.
 */
public class TupleTwoUnitTest {

   @Test
   public void test_equal(){
      assertEquals(new TupleTwo<>(2, "Hello"), new TupleTwo<>(2, "Hello"));
   }

   @Test
   public void test_hashCode(){
      assertEquals(new TupleTwo<>(2, "Hello").hashCode(), new TupleTwo<>(2, "Hello").hashCode());
   }
}
