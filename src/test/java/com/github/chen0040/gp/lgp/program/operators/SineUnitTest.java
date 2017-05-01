package com.github.chen0040.gp.lgp.program.operators;


import com.github.chen0040.gp.lgp.enums.OperatorExecutionStatus;
import com.github.chen0040.gp.lgp.program.Operator;
import com.github.chen0040.gp.lgp.program.Register;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 1/5/2017.
 */
public class SineUnitTest {

   @Test
   public void test(){
      Operator op = new Sine();

      Register reg1 = new Register();
      Register reg2 = new Register();
      Register destination = new Register();

      reg1.setValue(Math.PI / 2.0);

      assertThat(op.execute(reg1, reg2, destination)).isEqualTo(OperatorExecutionStatus.LGP_EXECUTE_NEXT_INSTRUCTION);

      assertThat(destination.getValue()).isEqualTo(1.0);
   }
}
