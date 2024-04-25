package com.example.demo.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RateResponse {

    private final TotalWinRate totalWinRate;
    private final List<ChampionWinRate> championWinRate;
    private final List<PositionWinRate> positionWinRate;

    public static RateResponse of(TotalWinRate totalWinRate, List<ChampionWinRate> championWinRate,
                                  List<PositionWinRate> positionWinRate) {
        return RateResponse.builder()
                .totalWinRate(totalWinRate)
                .championWinRate(championWinRate)
                .positionWinRate(positionWinRate)
                .build();
    }
}
