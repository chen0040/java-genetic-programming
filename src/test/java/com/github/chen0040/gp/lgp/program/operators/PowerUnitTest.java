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
public class PowerUnitTest {

   @Test
   public void test_not_exceed(){
      Operator op = new Power();

      Register reg1 = new Register();
      Register reg2 = new Register();
      Register destination = new Register();

      reg1.setValue(2);
      reg2.setValue(2);

      assertThat(op.execute(reg1, reg2, destination, null)).isEqualTo(OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION);

      assertThat(destination.getValue()).isEqualTo(4);
   }

   @Test
   public void test_exceed(){
      Operator op = new Power();

      Register reg1 = new Register();
      Register reg2 = new Register();
      Register destination = new Register();

      reg1.setValue(14);
      reg2.setValue(2);

      assertThat(op.execute(reg1, reg2, destination, null)).isEqualTo(OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION);

      assertThat(destination.getValue()).isEqualTo(17);
   }

   @Test
   public void test_makeCopy(){
      Operator op = new Power();
      assertEquals(op, op.makeCopy());
   }
}
