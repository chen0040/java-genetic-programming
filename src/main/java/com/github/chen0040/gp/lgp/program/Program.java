package com.github.chen0040.gp.lgp.program;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 2/5/2017.
 */
@Getter
@Setter
public class Program {
   private RegisterSet registerSet = new RegisterSet();
   private ConstantSet constantSet = new ConstantSet();
   private OperatorSet operatorSet = new OperatorSet();
   private List<Instruction> instructions = new ArrayList<>();

   private double fitness;
   private double objectiveValue;
   private boolean fitnessValid;


}
