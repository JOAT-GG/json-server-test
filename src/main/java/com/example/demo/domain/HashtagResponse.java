package com.example.demo.domain;

import lombok.*;

public class HashtagResponse {

    private final boolean isOverAverageDeath;
    private final boolean doesPlayerDieMidToLateOften;
    private final boolean doesPlayUnskilledChampRecently;

    @Builder
    private HashtagResponse(boolean isOverAverageDeath, boolean doesPlayerDieMidToLateOften, boolean doesPlayUnskilledChampRecently) {
        this.isOverAverageDeath = isOverAverageDeath;
        this.doesPlayerDieMidToLateOften = doesPlayerDieMidToLateOften;
        this.doesPlayUnskilledChampRecently = doesPlayUnskilledChampRecently;
    }

    public static HashtagResponse of(boolean isOverAverageDeath, boolean doesPlayerDieMidToLateOften, boolean doesPlayUnskilledChampRecently) {
        return HashtagResponse.builder()
                .isOverAverageDeath(isOverAverageDeath)
                .doesPlayerDieMidToLateOften(doesPlayerDieMidToLateOften)
                .doesPlayUnskilledChampRecently(doesPlayUnskilledChampRecently)
                .build();
    }
}
