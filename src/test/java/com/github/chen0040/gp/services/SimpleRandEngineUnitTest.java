package com.github.chen0040.gp.services;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 28/4/2017.
 * Simple random engine that uses java-built-in Random class for randomization
 */
public class SimpleRandEngineUnitTest {

   private RandEngine randEngine;

   @BeforeMethod
   public void setUp(){
      randEngine = new SimpleRandEngine();
   }

   @Test
   public void test_uniform(){
      assertThat(randEngine.uniform()).isBetween(0.0, 1.0);
   }

}
