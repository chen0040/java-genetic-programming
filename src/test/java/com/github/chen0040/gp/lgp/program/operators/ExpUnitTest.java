package com.github.chen0040.gp.lgp.program.operators;


import com.github.chen0040.gp.lgp.enums.OperatorExecutionStatus;
import com.github.chen0040.gp.lgp.program.Operator;
import com.github.chen0040.gp.lgp.program.Register;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.assertEquals;


/**
 * Created by xschen on 1/5/2017.
 */
public class ExpUnitTest {

   @Test
   public void test(){
      Operator op = new Exp();

      Register reg1 = new Register();
      Register reg2 = new Register();
      Register destination = new Register();

      reg1.setValue(-1);

      assertThat(op.execute(reg1, reg2, destination, null)).isEqualTo(OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION);

      assertThat(destination.getValue()).isEqualTo(Math.exp(-1));
   }

   @Test
   public void test_makeCopy(){
      Operator op = new Exp();
      assertEquals(op, op.makeCopy());
   }
}
