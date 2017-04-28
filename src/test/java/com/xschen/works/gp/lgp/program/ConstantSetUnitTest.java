package com.xschen.works.gp.lgp.program;


import com.xschen.works.gp.services.RandEngine;
import com.xschen.works.gp.services.SimpleRandEngine;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 28/4/2017.
 * Unit test for the ConstantSet
 */
public class ConstantSetUnitTest {

   private ConstantSet constantSet;
   private RandEngine randEngine;

   @BeforeMethod
   public void setUp(){
      constantSet = new ConstantSet();
      randEngine = new SimpleRandEngine();

      for(int i=0; i < 10; i++) {
         constantSet.add(0.1 * i, 1.0);
      }
   }

   @Test
   public void test_size(){
      assertThat(constantSet.size()).isEqualTo(10);
   }

   @Test
   public void test_makeCopy(){
      assertThat(constantSet.makeCopy()).isEqualTo(constantSet);
   }

   @Test
   public void test_anyOther(){
      for(int i=0; i < 4; ++i) {
         Register register = constantSet.anyOther(constantSet.get(i), randEngine);
         assertThat(register).isNotEqualTo(constantSet.get(i));
      }
   }

}
