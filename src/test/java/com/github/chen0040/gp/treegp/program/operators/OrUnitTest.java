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
      op1.read(Arrays.asList(1.0, 0.2));
      op1.execute();
      assertThat(op1.getValue()).isEqualTo(1.0);

      Or op2 = new Or();
      op2.read(Arrays.asList(0.0, 0.2));
      op2.execute();
      assertThat(op2.getValue()).isEqualTo(1.0);

      Or op3 = new Or();
      op3.read(Arrays.asList(1.0, 0.0));
      op3.execute();
      assertThat(op3.getValue()).isEqualTo(1.0);

      Or op4 = new Or();
      op4.read(Arrays.asList(0.0, 0.0));
      op4.execute();
      assertThat(op4.getValue()).isEqualTo(0.0);
   }
}
