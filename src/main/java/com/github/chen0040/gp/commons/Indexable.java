package com.github.chen0040.gp.commons;


/**
 * Created by xschen on 29/4/2017.
 */
public interface Indexable<T> {
   int getIndex();
   void setIndex(int index);
   T makeCopy();
   String getName();
}
