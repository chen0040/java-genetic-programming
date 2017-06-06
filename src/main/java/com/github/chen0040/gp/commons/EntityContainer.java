package com.github.chen0040.gp.commons;

import com.github.chen0040.gp.services.RandEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xschen on 29/4/2017.
 */
public class EntityContainer<T extends Indexable<T>> implements Serializable {
   private static final long serialVersionUID = 1564461409545848260L;
   protected final List<T> entities = new ArrayList<>();
   protected final List<Double> weights = new ArrayList<>();
   protected final Map<String, Integer> names = new HashMap<>();

   public EntityContainer(){

   }

   public double weightSum() {
      if(weights.isEmpty()){
         return 0.0;
      }
      double sum = 0;
      for(Double weight : weights){
         sum += weight;
      }
      return sum;
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

   public T get(String name) {
      if(!names.containsKey(name)){
         return null;
      }
      return entities.get(names.get(name));
   }

   public int size() {
      return entities.size();
   }

   public void add(T entity, double weight) {
      entity.setIndex(entities.size());
      entities.add(entity);
      weights.add(weight);
      names.put(entity.getName(), entity.getIndex());
   }
   
   public void add(T entity){
      add(entity, 1.0);
   }




   public void copy(EntityContainer<T> that){
      this.entities.clear();
      this.weights.clear();
      this.names.clear();

      for(int i = 0; i < that.entities.size(); ++i){
         this.entities.add(that.entities.get(i).makeCopy());
         this.weights.add(that.weights.get(i));
      }
      this.names.putAll(that.names);
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


   public T any(RandEngine randEngine) {
      return anyOther(null, randEngine);
   }


   public double getWeight(int index) {
      return weights.get(index);
   }


   public void addAll(T... entities) {
      for(int i=0; i < entities.length; ++i) {
         add(entities[i]);
      }
   }
}
