package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.exceptions.SizeMismatchedException;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 11/5/2017.
 */
public class SqrtUnitTest {

   @Test
   public void test_makeCopy(){
      Sqrt op = new Sqrt();
      assertThat(op.makeCopy()).isEqualTo(op);
   }

   @Test
   public void test_execute(){
      Sqrt op = new Sqrt();
      double input = 0.2;
      op.read(Collections.singletonList(input));
      op.execute();
      assertThat(op.getValue()).isEqualTo(Math.sqrt(input));
   }

   @Test(expectedExceptions = SizeMismatchedException.class)
   public void test_readException() {
      Sqrt op = new Sqrt();
      op.read(Arrays.asList(0.1, 0.2));
   }

}
