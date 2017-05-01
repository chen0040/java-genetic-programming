package com.github.chen0040.gp.lgp.program.operators;


import com.github.chen0040.gp.lgp.enums.OperatorExecutionStatus;
import com.github.chen0040.gp.lgp.program.Operator;
import com.github.chen0040.gp.lgp.program.Register;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 1/5/2017.
 */
public class IfGreaterThanTest {

   @Test
   public void test_greater() {
      Operator op = new IfGreaterThan();

      Register reg1 = new Register();
      Register reg2 = new Register();

      reg1.setValue(3);
      reg2.setValue(1);

      Register destination = new Register();

      assertThat(op.execute(reg1, reg2, destination)).isEqualTo(OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION);
   }

   @Test
   public void test_not_greater() {
      Operator op = new IfGreaterThan();

      Register reg1 = new Register();
      Register reg2 = new Register();

      reg1.setValue(1);
      reg2.setValue(3);

      Register destination = new Register();

      assertThat(op.execute(reg1, reg2, destination)).isEqualTo(OperatorExecutionStatus.LGP_SKIP_NEXT_INSTRUCTION);
   }

}
