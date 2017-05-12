package com.github.chen0040.gp.treegp.program;


import com.github.chen0040.gp.services.RandEngine;
import com.github.chen0040.gp.services.SimpleRandEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 28/4/2017.
 * Unit test for the VariableSet
 */
public class VariableSetUnitTest {

   private VariableSet registerSet;
   private RandEngine randEngine;
   private static final Logger logger = LoggerFactory.getLogger(VariableSetUnitTest.class);

   @BeforeMethod
   public void setUp(){
      registerSet = new VariableSet();
      randEngine = new SimpleRandEngine();

      for(int i=0; i < 10; i++) {
         registerSet.add("v" + (i+1), 0.1 * i, 1.0);
      }
   }

   @Test
   public void test_readOnly(){
      for(int i=0; i < registerSet.size(); ++i){
         assertThat(registerSet.get(i).isReadOnly()).isFalse();
      }
      logger.info("{}", registerSet);
   }

   @Test
   public void test_size(){
      assertThat(registerSet.size()).isEqualTo(10);
   }

   @Test
   public void test_makeCopy(){
      assertThat(registerSet.makeCopy()).isEqualTo(registerSet);
   }

   @Test
   public void test_anyOther(){
      for(int i=0; i < 4; ++i) {
         Terminal register = registerSet.anyOther(registerSet.get(i), randEngine);
         assertThat(register).isNotEqualTo(registerSet.get(i));
      }
   }

}
