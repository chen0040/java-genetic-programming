package com.xschen.works.gp.lgp.program;


import com.xschen.works.gp.services.RandEngine;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.io.Serializable;


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
public class Register implements Serializable, Indexable<Register> {

   private static final long serialVersionUID = 8423690373685553734L;
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

   public void mutate(RandEngine randomEngine, double sd){
      value += randomEngine.normal(0, 1.0) * sd;
   }

   @Override
   public  String toString(){
      return (constant ? "c" : "r") + "[" + index + "]";
   }


   @Override public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      Register register = (Register) o;

      if (constant != register.constant)
         return false;
      if (Double.compare(register.value, value) != 0)
         return false;
      return index == register.index;

   }


   @Override public int hashCode() {
      int result;
      long temp;
      result = (constant ? 1 : 0);
      temp = Double.doubleToLongBits(value);
      result = 31 * result + (int) (temp ^ (temp >>> 32));
      result = 31 * result + index;
      return result;
   }
}
