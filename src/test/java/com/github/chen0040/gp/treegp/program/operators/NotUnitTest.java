package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.exceptions.SizeMismatchedException;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 11/5/2017.
 */
public class NotUnitTest {

   private static final double tolerance = 0.0000001;

   @Test
   public void test_makeCopy(){
      Not op = new Not();
      assertThat(op.makeCopy()).isEqualTo(op);
   }

   @Test
   public void test_execute(){
      Not op = new Not();
      double input = 0.2;
      op.read(Collections.singletonList(input));
      op.execute();
      assertThat(op.getValue()).isEqualTo(isTrue(input) ? 1.0 : 0.0);
   }

   @Test(expectedExceptions = SizeMismatchedException.class)
   public void test_readException() {
      Not op = new Not();
      op.read(Arrays.asList(0.1, 0.2));
   }

   public boolean isTrue(double x)
   {
      if (x > -tolerance && x < tolerance)
      {
         return false;
      }
      return true;
   }



}
