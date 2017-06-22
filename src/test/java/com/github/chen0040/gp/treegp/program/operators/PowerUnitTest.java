package com.github.chen0040.gp.treegp.program.operators;


import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 11/5/2017.
 */
public class PowerUnitTest {

   @Test
   public void test_makeCopy(){
      Power op = new Power();
      assertThat(op.arity()).isEqualTo(2);
      assertThat(op.makeCopy()).isEqualTo(op);
   }

   @Test
   public void test_execute(){
      Power op = new Power();
      op.read(Arrays.asList(2.0, 2.0));
      op.execute(null);
      assertThat(op.getValue()).isEqualTo(4.0);
   }

   @Test
   public void test_execute_large(){
      Power op = new Power();
      op.read(Arrays.asList(12.0, 0.0));
      op.execute(null);
      assertThat(op.getValue()).isEqualTo(13.0);
   }
}
