package com.example.demo.domain;

import lombok.*;

@Getter
public class HashtagResponse {

    private final boolean isOverAverageDeath;
    private final boolean doesPlayerDieMidToLateOften;
    private final boolean playedUnskilledChampRecently;

    @Builder
    private HashtagResponse(boolean isOverAverageDeath, boolean doesPlayerDieMidToLateOften, boolean playedUnskilledChampRecently) {
        this.isOverAverageDeath = isOverAverageDeath;
        this.doesPlayerDieMidToLateOften = doesPlayerDieMidToLateOften;
        this.playedUnskilledChampRecently = playedUnskilledChampRecently;
    }

    public static HashtagResponse of(boolean isOverAverageDeath, boolean doesPlayerDieMidToLateOften, boolean doesPlayUnskilledChampRecently) {
        return HashtagResponse.builder()
                .isOverAverageDeath(isOverAverageDeath)
                .doesPlayerDieMidToLateOften(doesPlayerDieMidToLateOften)
                .playedUnskilledChampRecently(doesPlayUnskilledChampRecently)
                .build();
    }
}
