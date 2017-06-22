package com.github.chen0040.gp.treegp.program.operators;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 11/5/2017.
 */
public class OrUnitTest {

   private static final Logger logger = LoggerFactory.getLogger(OrUnitTest.class);

   @Test
   public void test_makeCopy(){
      Or op = new Or();
      assertThat(op.arity()).isEqualTo(2);
      assertThat(op.makeCopy()).isEqualTo(op);
      logger.info("{}", op);
   }

   @Test
   public void test_execute(){
      Or op1 = new Or();
      op1.beforeExecute(Arrays.asList(1.0, 0.2), null);
      op1.execute(null);
      assertThat(op1.getValue()).isEqualTo(1.0);

      Or op2 = new Or();
      op2.beforeExecute(Arrays.asList(0.0, 0.2), null);
      op2.execute(null);
      assertThat(op2.getValue()).isEqualTo(1.0);

      Or op3 = new Or();
      op3.beforeExecute(Arrays.asList(1.0, 0.0), null);
      op3.execute(null);
      assertThat(op3.getValue()).isEqualTo(1.0);

      Or op4 = new Or();
      op4.beforeExecute(Arrays.asList(0.0, 0.0), null);
      op4.execute(null);
      assertThat(op4.getValue()).isEqualTo(0.0);
   }
}
