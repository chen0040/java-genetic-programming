package com.github.chen0040.gp.commons;


import com.github.chen0040.data.utils.TupleTwo;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 4/5/2017.
 */
@Getter
@Setter
public class TournamentSelectionResult<T> {

   private TupleTwo<T, T> winners;
   private TupleTwo<T, T> losers;
}
