package com.github.chen0040.gp.utils;


/**
 * Created by xschen on 4/5/2017.
 */
public class TupleTwo<T> {
   private final T v1;
   private final T v2;

   public TupleTwo(T v1, T v2) {
      this.v1 = v1;
      this.v2 = v2;
   }

   public T _1(){
      return v1;
   }

   public T _2(){
      return v2;
   }
}
