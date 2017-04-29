package com.xschen.works.gp.lgp.program;


import com.xschen.works.gp.services.RandEngine;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 29/4/2017.
 */
public class EntityContainer<T extends Indexable<T>> {
   protected final List<T> entities = new ArrayList<>();
   protected final List<Double> weights = new ArrayList<>();

   public EntityContainer(){

   }

   public double weightSum() {
      if(weights.isEmpty()){
         return 0;
      }
      return weights.stream().reduce((a, b ) -> a + b).get();
   }

   public T anyOther(T excluded, RandEngine rand){
      double weightSum = weightSum();
      for(int attempts = 0; attempts < 10; attempts++){
         double r = weightSum * rand.uniform();
         double accSum = 0;
         for(int i = 0; i < entities.size(); ++i){
            accSum += weights.get(i);
            if(r <= accSum){
               if(entities.get(i) == excluded){
                  break;
               } else {
                  return entities.get(i);
               }
            }
         }
      }

      return excluded;
   }

   public T get(int index){
      return entities.get(index);
   }

   public int size() {
      return entities.size();
   }

   public void add(T register, double weight) {
      register.setIndex(entities.size());
      entities.add(register);
      weights.add(weight);
   }


   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < entities.size(); ++i){
         if(i != 0){
            sb.append("\r\n");
         }
         sb.append("register[").append(i).append("]: ").append(entities.get(i));
      }
      return sb.toString();
   }

   public void copy(EntityContainer<T> that){
      for(int i = 0; i < that.entities.size(); ++i){
         this.entities.add(that.entities.get(i).makeCopy());
         this.weights.add(that.weights.get(i));
      }
   }


   @Override public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || !(o instanceof EntityContainer))
         return false;

      EntityContainer<T> that = (EntityContainer<T>) o;

      if(entities.size() != that.entities.size()){
         return false;
      }

      for(int i=0; i < entities.size(); ++i){
         if(!entities.get(i).equals(that.entities.get(i))) {
            return false;
         }
      }
      return weights.equals(that.weights);

   }


   @Override public int hashCode() {
      int result = entities.hashCode();
      result = 31 * result + weights.hashCode();
      return result;
   }
}
