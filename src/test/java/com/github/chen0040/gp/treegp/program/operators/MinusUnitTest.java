package com.github.chen0040.gp.treegp.program.operators;


import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 11/5/2017.
 */
public class MinusUnitTest {

   @Test
   public void test_makeCopy(){
      Minus op = new Minus();
      assertThat(op.arity()).isEqualTo(2);
      assertThat(op.makeCopy()).isEqualTo(op);
   }

   @Test
   public void test_execute(){
      Minus op = new Minus();
      op.beforeExecute(Arrays.asList(2.0, 2.0), null);
      op.execute(null);
      assertThat(op.getValue()).isEqualTo(0.0);
   }

}
