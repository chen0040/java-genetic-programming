package com.github.chen0040.gp.treegp.program.operators;


import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 11/5/2017.
 */
public class PlusUnitTest {

   @Test
   public void test_makeCopy(){
      Plus op = new Plus();
      assertThat(op.arity()).isEqualTo(2);
      assertThat(op.makeCopy()).isEqualTo(op);
   }

   @Test
   public void test_execute(){
      Plus op = new Plus();
      op.read(Arrays.asList(2.0, 2.0));
      op.execute();
      assertThat(op.getValue()).isEqualTo(4.0);
   }
}
