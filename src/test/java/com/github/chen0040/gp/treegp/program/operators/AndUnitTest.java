package com.github.chen0040.gp.treegp.program.operators;


import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;


/**
 * Created by xschen on 11/5/2017.
 */
public class AndUnitTest {

   @Test
   public void test_makeCopy(){
      And op = new And();
      assertThat(op.makeCopy()).isEqualTo(op);
   }

   @Test
   public void test_execute(){
      And op1 = new And();
      op1.read(Arrays.asList(1.0, 0.2));
      op1.execute();
      assertThat(op1.getValue()).isEqualTo(1.0);

      And op2 = new And();
      op2.read(Arrays.asList(0.0, 0.2));
      op2.execute();
      assertThat(op2.getValue()).isEqualTo(0.0);

      And op3 = new And();
      op3.read(Arrays.asList(1.0, 0.0));
      op3.execute();
      assertThat(op3.getValue()).isEqualTo(0.0);

      And op4 = new And();
      op4.read(Arrays.asList(0.0, 0.0));
      op4.execute();
      assertThat(op4.getValue()).isEqualTo(0.0);
   }
}
