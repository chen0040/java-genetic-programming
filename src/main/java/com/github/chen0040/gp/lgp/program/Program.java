package com.github.chen0040.gp.lgp.program;


import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 2/5/2017.
 */
@Getter
@Setter
public class Program {
   private RegisterSet registerSet = new RegisterSet();
   private ConstantSet constantSet = new ConstantSet();
   private OperatorSet operatorSet = new OperatorSet();
}
