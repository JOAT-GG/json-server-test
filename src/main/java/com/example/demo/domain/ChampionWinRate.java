package com.example.demo.domain;

import com.example.demo.util.DoubleRoundUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ChampionWinRate {

    private String name;
    private double rate;

    public ChampionWinRate(String name, int winCnt, long playCnt) {
        this.name = name;
        this.rate = DoubleRoundUtil.round((winCnt == 0 ? 0 : ((double) winCnt / playCnt) * 100));
    }
}
