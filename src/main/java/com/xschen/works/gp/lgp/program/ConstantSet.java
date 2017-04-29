package com.xschen.works.gp.lgp.program;


import com.xschen.works.gp.services.RandEngine;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 28/4/2017.
 * Containers for the read-only registers
 */
public class ConstantSet extends RegisterSet {
   public ConstantSet(){
      super();
   }

   @Override
   public boolean isReadOnly(){
      return true;
   }


}
