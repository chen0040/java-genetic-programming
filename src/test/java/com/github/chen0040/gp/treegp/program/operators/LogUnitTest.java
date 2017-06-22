package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.exceptions.SizeMismatchedException;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 11/5/2017.
 */
public class LogUnitTest {

   @Test
   public void test_makeCopy(){
      Log op = new Log();
      assertThat(op.arity()).isEqualTo(1);
      assertThat(op.makeCopy()).isEqualTo(op);
   }

   @Test
   public void test_execute(){
      Log op = new Log();
      double input = 0.2;
      op.read(Collections.singletonList(input));
      op.execute(null);
      assertThat(op.getValue()).isEqualTo(Math.log(input));
   }

   @Test(expectedExceptions = SizeMismatchedException.class)
   public void test_readException() {
      Log op = new Log();
      op.read(Arrays.asList(0.1, 0.2));
   }

}
