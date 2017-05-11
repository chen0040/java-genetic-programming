package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.treegp.program.Operator;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 11/5/2017.
 */
public class IfLessThanUnitTest {

   @Test
   public void test_makeCopy(){
      IfLessThan op1 = new IfLessThan();
      Operator op2 = op1.makeCopy();
      assertThat(op1).isEqualTo(op2);
   }

   @Test
   public void test_ifNotLessThan(){
      IfLessThan op1 = new IfLessThan();
      op1.read(Arrays.asList(0.1, 0.05, 1.0, 2.0));
      op1.execute();
      assertThat(op1.getValue()).isEqualTo(2.0);
   }

   @Test
   public void test_ifLessThan(){
      IfLessThan op1 = new IfLessThan();
      op1.read(Arrays.asList(0.1, 0.5, 1.0, 2.0));
      op1.execute();
      assertThat(op1.getValue()).isEqualTo(1.0);
   }

   @Test(expectedExceptions = RuntimeException.class)
   public void test_readException(){

      IfLessThan op1 = new IfLessThan();
      op1.read(Arrays.asList(0.1, 0.5, 1.0));
   }

}
