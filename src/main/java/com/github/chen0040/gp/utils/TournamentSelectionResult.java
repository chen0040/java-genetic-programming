package com.github.chen0040.gp.utils;


import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 4/5/2017.
 */
@Getter
@Setter
public class TournamentSelectionResult<T> {

   private TupleTwo<T> winners;
   private TupleTwo<T> losers;
}
