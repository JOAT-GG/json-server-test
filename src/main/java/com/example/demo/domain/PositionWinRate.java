package com.example.demo.domain;

import com.example.demo.util.DoubleRoundUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class PositionWinRate {

    private String position;
    private double rate;

    public PositionWinRate(String position, int winCnt, long playCnt) {
        this.position = position;
        this.rate = DoubleRoundUtil.round(winCnt == 0 ? 0 : ((double) winCnt / playCnt) * 100);
    }
}
