package com.xschen.works.gp.lgp.program;


import com.xschen.works.gp.services.RandEngine;
import com.xschen.works.gp.services.SimpleRandEngine;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 28/4/2017.
 * Unit test for the OperatorSet
 */
public class OperatorSetUnitTest {

   private OperatorSet operatorSet;
   private RandEngine randEngine;

   @BeforeMethod
   public void setUp(){
      operatorSet = new OperatorSet();
      randEngine = new SimpleRandEngine();

      for(int i=0; i < 10; i++) {
         operatorSet.addIfGreaterThanOperator();
         operatorSet.addIfLessThanOperator();
      }
   }


   @Test
   public void test_size(){
      assertThat(operatorSet.size()).isEqualTo(20);
   }

   @Test
   public void test_makeCopy(){
      assertThat(operatorSet.makeCopy()).isEqualTo(operatorSet);
   }

   @Test
   public void test_anyOther(){
      for(int i=0; i < 4; ++i) {
         Operator operator = operatorSet.anyOther(operatorSet.get(i), randEngine);
         assertThat(operator).isNotEqualTo(operatorSet.get(i));
      }
   }

}
