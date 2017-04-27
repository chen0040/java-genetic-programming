package com.xschen.works.gp.lgp.program;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.within;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;


/**
 * Created by xschen on 27/4/2017.
 */
public class RegisterUnitTest {

   private Register register;
   private NormalDistribution stdNormalDistribution;

   @BeforeMethod
   public void setUp(){
      register = new Register();

      register.setConstant(true);
      register.setValue(0.1);
      register.setIndex(0);

      stdNormalDistribution = Mockito.mock(NormalDistribution.class);
      Mockito.when(stdNormalDistribution.sample()).thenReturn(0.1);
   }

   @Test
   public void test_register_get(){
      assertThat(register.getIndex()).isEqualTo(0);
      assertTrue(register.isConstant());
      assertThat(register.getValue()).isEqualTo(0.1);
   }

   @Test
   public void test_mutate(){
      register.mutate(stdNormalDistribution, 0.2);
      assertThat(register.getValue()).isCloseTo(0.12, within(0.01));
   }

   @Test
   public void test_makeCopy() {
      Register clone = register.makeCopy();

      assertThat(clone.getIndex()).isEqualTo(0);
      assertTrue(clone.isConstant());
      assertThat(clone.getValue()).isEqualTo(0.1);
   }

}
