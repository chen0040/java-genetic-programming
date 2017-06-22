package com.github.chen0040.gp.treegp.program.operators;


import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;


/**
 * Created by xschen on 11/5/2017.
 */
public class DivideUnitTest {

   @Test
   public void test_makeCopy(){
      Divide op = new Divide();
      assertThat(op.arity()).isEqualTo(2);
      assertThat(op.makeCopy()).isEqualTo(op);
   }

   @Test
   public void test_execute(){
      Divide op = new Divide();
      op.read(Arrays.asList(2.0, 2.0));
      op.execute(null);
      assertThat(op.getValue()).isEqualTo(1.0);
   }

   @Test
   public void test_execute_divideByZero(){
      Divide op = new Divide();
      op.read(Arrays.asList(2.0, 0.0));
      op.execute(null);
      assertThat(op.getValue()).isEqualTo(2.0);
   }
}
