package com.xschen.works.gp.lgp.program.operators;


import com.xschen.works.gp.lgp.enums.OperatorExecutionStatus;
import com.xschen.works.gp.lgp.program.Operator;
import com.xschen.works.gp.lgp.program.ProgramManager;
import com.xschen.works.gp.lgp.program.Register;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 1/5/2017.
 */
public class LogUnitTest {

   @Test
   public void test_log_by_nonzero(){
      Operator op = new Log();

      Register reg1 = new Register();
      Register reg2 = new Register();
      Register destination = new Register();

      reg1.setValue(2);

      assertThat(op.execute(reg1, reg2, destination)).isEqualTo(OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION);

      assertThat(destination.getValue()).isEqualTo(Math.log(2));
   }

   @Test
   public void test_log_by_zero(){
      Operator op = new Log();

      Register reg1 = new Register();
      Register reg2 = new Register();
      Register destination = new Register();

      reg1.setValue(0);

      assertThat(op.execute(reg1, reg2, destination)).isEqualTo(OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION);

      assertThat(destination.getValue()).isEqualTo(ProgramManager.DEFAULT_UNDEFINED_LOW);
   }
}
