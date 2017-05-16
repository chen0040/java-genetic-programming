package com.github.chen0040.gp.treegp.gp;


import com.github.chen0040.gp.treegp.TreeGP;
import com.github.chen0040.gp.treegp.program.Solution;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Created by xschen on 15/5/2017.
 */
@Getter
@Setter
public class Population {

   private final List<Solution> solutions = new ArrayList<>();
   private final TreeGP manager;

   @Setter(AccessLevel.NONE)
   @Getter(AccessLevel.NONE)
   private Optional<Solution> globalBestSolution = Optional.empty();

   private Solution bestSolutionInCurrentGeneration = null;
   private int currentGenertion;

   public Population(TreeGP manager) {
      this.manager = manager;
   }

   public void initialize(){

   }
}
