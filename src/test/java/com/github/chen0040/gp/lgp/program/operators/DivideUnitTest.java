package com.github.chen0040.gp.lgp.program.operators;


import com.github.chen0040.gp.lgp.enums.OperatorExecutionStatus;
import com.github.chen0040.gp.lgp.program.Operator;
import com.github.chen0040.gp.lgp.program.Register;
import com.github.chen0040.gp.lgp.LGP;
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

      assertThat(op.execute(reg1, reg2, destination)).isEqualTo(OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION);

      assertThat(destination.getValue()).isEqualTo(1);
   }

   @Test
   public void test_divide_by_zero(){
      Operator op = new Divide();

      Register reg1 = new Register();
      Register reg2 = new Register();
      Register destination = new Register();

      reg1.setValue(2);
      reg2.setValue(0);

      assertThat(op.execute(reg1, reg2, destination)).isEqualTo(OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION);

      assertThat(destination.getValue()).isEqualTo(2 + LGP.DEFAULT_UNDEFINED_LOW);
   }
}
