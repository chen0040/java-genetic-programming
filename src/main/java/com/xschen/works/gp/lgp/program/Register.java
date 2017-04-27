package com.xschen.works.gp.lgp.program;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.distribution.NormalDistribution;


/**
 * Created by xschen on 27/4/2017.
 * This class represents a register in a linear program
 *
 * <ol>
 *    <li>a register can be read-only, in which case it is a constant</li>
 *    <li>a register is continuous numeric variable in a linear program</li>
 * </ol>
 */
@Getter
@Setter
public class Register {

   // denote whether the register is read-only
   private boolean constant;

   // value stored in the register
   private double value;

   // the index of the register within the linear program
   private int index;

   public Register makeCopy(){
      Register clone = new Register();
      clone.setConstant(constant);
      clone.setValue(value);
      clone.setIndex(index);

      return clone;
   }

   public void mutate(NormalDistribution stdNormalDistribution, double sd){
      value += stdNormalDistribution.sample() * sd;
   }

   @Override
   public  String toString(){
      return (constant ? "c" : "r") + "[" + index + "]";
   }


}
