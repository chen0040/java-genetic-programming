package com.github.chen0040.gp.utils;


import com.github.chen0040.gp.services.SimpleRandEngine;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;


/**
 * Created by xschen on 8/5/2017.
 */
public class TournamentSelectionUnitTest {

   @Test
   public void test_select(){
      List<Double> a = Arrays.asList(0.1, 0.2, 0.3, 0.4, 0.5);
      TournamentSelectionResult<Double> result = TournamentSelection.select(a, new SimpleRandEngine());
      TupleTwo<Double, Double> losers = result.getLosers();
      TupleTwo<Double, Double> winners = result.getWinners();
      assertThat(losers._1()).isNotEqualTo(losers._2());
      assertThat(winners._1()).isNotEqualTo(winners._2());
      assertThat(losers._1()).isGreaterThan(winners._1());
      assertThat(losers._2()).isGreaterThan(winners._2());
   }
}
