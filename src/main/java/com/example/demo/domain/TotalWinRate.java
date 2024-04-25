package com.example.demo.domain;

import com.example.demo.util.DoubleRoundUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class TotalWinRate {

    private int wins;
    private int looses;
    private double rate;

    public TotalWinRate(int wins, int looses) {
        this.wins = wins;
        this.looses = looses;
        this.rate = DoubleRoundUtil.round(((double) wins / (wins + looses)) * 100);
    }


}
