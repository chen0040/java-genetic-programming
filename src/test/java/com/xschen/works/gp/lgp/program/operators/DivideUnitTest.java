package com.xschen.works.gp.lgp.program.operators;


import com.xschen.works.gp.lgp.program.Operator;
import com.xschen.works.gp.lgp.program.Register;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 1/5/2017.
 */
public class DivideUnitTest {

   @Test
   public void test_divide_by_nonzero(){
      Operator op = new Divide();

      Register reg1 = new Register();
      Register reg2 = new Register();
      Register destination = new Register();

      reg1.setValue(2);
      reg2.setValue(2);

      op.execute(reg1, reg2, destination);

      assertThat(destination.getValue()).isEqualTo(1);
   }
}
