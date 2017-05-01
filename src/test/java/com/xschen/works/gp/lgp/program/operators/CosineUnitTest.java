package com.xschen.works.gp.lgp.program.operators;


import com.xschen.works.gp.lgp.program.Operator;
import com.xschen.works.gp.lgp.program.Register;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;


/**
 * Created by xschen on 1/5/2017.
 */
public class CosineUnitTest {

   @Test
   public void test(){
      Operator op = new Cosine();

      Register reg1 = new Register();
      Register reg2 = new Register();
      Register destination = new Register();

      reg1.setValue(Math.PI);

      op.execute(reg1, reg2, destination);

      assertThat(destination.getValue()).isEqualTo(-1.0);
   }
}
