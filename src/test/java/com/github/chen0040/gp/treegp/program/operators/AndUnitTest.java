package com.github.chen0040.gp.treegp.program.operators;


import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 11/5/2017.
 */
public class AndUnitTest {

   @Test
   public void test_makeCopy(){
      And op = new And();
      assertThat(op.arity()).isEqualTo(2);
      assertThat(op.makeCopy()).isEqualTo(op);
   }

   @Test
   public void test_execute(){
      And op1 = new And();
      op1.beforeExecute(Arrays.asList(1.0, 0.2), null);
      op1.execute(null);
      assertThat(op1.getValue()).isEqualTo(1.0);

      And op2 = new And();
      op2.beforeExecute(Arrays.asList(0.0, 0.2), null);
      op2.execute(null);
      assertThat(op2.getValue()).isEqualTo(0.0);

      And op3 = new And();
      op3.beforeExecute(Arrays.asList(1.0, 0.0), null);
      op3.execute(null);
      assertThat(op3.getValue()).isEqualTo(0.0);

      And op4 = new And();
      op4.beforeExecute(Arrays.asList(0.0, 0.0), null);
      op4.execute(null);
      assertThat(op4.getValue()).isEqualTo(0.0);
   }
}
