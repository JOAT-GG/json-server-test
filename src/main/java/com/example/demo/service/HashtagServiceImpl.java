package com.example.demo.service;

import com.example.demo.domain.*;
import org.springframework.stereotype.*;

@Service
public class HashtagServiceImpl implements HashtagService {

    // 플레이어의 해시 태그를
    @Override
    public HashtagResponse getPlayerHashtags(String gameName, String tagLine) {
        // 1. summonerName 으로 puuid 조회

        // 2. puuid 를 통해 matchV5 에서 최근 랭크 게임 20 게임 가져오기

        // 3. 불러온 경기 데이터로 지표 계산
        //      1. 팀 평균 데스보다 많은가?                 -> isOverAverageDeath()
        //      2. 중후반에 많이 죽냐? (기준은 일단 5데스)     -> doesPlayerDieMidToLateOften()
        //      3. 안하던 챔프를 최근에 하냐                 -> doesPlayUnskilledChampRecently()

        // 4. 반환
        return null;
    }


}
