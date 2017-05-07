package com.github.chen0040.gp.services;


import java.util.Random;


/**
 * Created by xschen on 28/4/2017.
 * Interface for the random engine which provides random number to the package
 */
public interface RandEngine {
   double uniform();

   double normal(double mean, double sd);

   int nextInt(int upper);

   int nextInt(int lower, int upper);
}
