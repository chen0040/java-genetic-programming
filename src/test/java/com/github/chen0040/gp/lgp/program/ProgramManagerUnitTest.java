package com.github.chen0040.gp.lgp.program;


import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 29/4/2017.
 */
public class ProgramManagerUnitTest {

   @Test
   public void test_undefined(){
      ProgramManager pm = new ProgramManager();
      assertThat(pm.undefined()).isEqualTo(pm.getUndefinedLow());
   }
}
