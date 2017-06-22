package com.github.chen0040.gp.treegp.program.operators;


import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 11/5/2017.
 */
public class ModuloUnitTest {

   @Test
   public void test_makeCopy(){
      Modulo op = new Modulo();
      assertThat(op.arity()).isEqualTo(2);
      assertThat(op.makeCopy()).isEqualTo(op);
   }

   @Test
   public void test_execute(){
      Modulo op = new Modulo();
      op.beforeExecute(Arrays.asList(3.5, 2.0), null);
      op.execute(null);
      assertThat(op.getValue()).isEqualTo(1.5);
   }

   @Test
   public void test_execute_neg(){
      Modulo op = new Modulo();
      op.beforeExecute(Arrays.asList(-3.5, -2.0), null);
      op.execute(null);
      assertThat(op.getValue()).isEqualTo(1.5);
   }

   @Test
   public void test_execute_moduloByZero(){
      Modulo op = new Modulo();
      op.beforeExecute(Arrays.asList(2.0, 0.0), null);
      op.execute(null);
      assertThat(op.getValue()).isEqualTo(2.0);
   }
}
