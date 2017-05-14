package com.github.chen0040.gp.treegp;


import com.github.chen0040.gp.services.RandEngine;
import com.github.chen0040.gp.services.SimpleRandEngine;
import com.github.chen0040.gp.treegp.enums.TGPCrossoverStrategy;
import com.github.chen0040.gp.treegp.enums.TGPInitializationStrategy;
import com.github.chen0040.gp.treegp.enums.TGPMutationStrategy;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 14/5/2017.
 */
@Getter
@Setter
public class TreeGP {
   private TGPInitializationStrategy initializationStrategy;
   private TGPCrossoverStrategy crossoverStrategy;
   private TGPMutationStrategy mutationStrategy;

   private RandEngine randEngine = new SimpleRandEngine();
   private int maxDepthForCrossover;
   private int maxProgramDepth;
}
