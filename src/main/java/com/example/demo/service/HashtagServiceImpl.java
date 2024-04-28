package com.example.demo.service;

import com.example.demo.domain.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.util.*;

@Service
public class HashtagServiceImpl implements HashtagService {

    private final RestTemplate restTemplate;

    public HashtagServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // API Key는 외부 설정으로부터 주입받거나 보안이 강화된 방식으로 관리되어야 합니다.
    private static final String API_KEY = "RGAPI-ce054f05-d3b8-4b29-9532-819b83a22364";
    private static final String RIOT_ACCOUNT_API = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/{summonerName}/{tagLine}?api_key=" + API_KEY;
    private static final String RIOT_MATCHES_API = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/{puuid}/ids?type=ranked&start=0&count=20&api_key=" + API_KEY;
    private static final String RIOT_MATCH_API = "https://asia.api.riotgames.com/lol/match/v5/matches/{matchId}?api_key=" + API_KEY;
    private static final String RIOT_MATCH_TIMELINE_DATA_API = "https://asia.api.riotgames.com/lol/match/v5/matches/{matchId}/timeline?api_key=" + API_KEY;


    @Override
    public HashtagResponse getPlayerHashtags(String gameName, String tagLine) {
        // 1. summonerName 으로 puuid 조회
        String accountUrl = RIOT_ACCOUNT_API.replace("{summonerName}", gameName)
                .replace("{tagLine}", tagLine);
        AccountResponse account = restTemplate.getForObject(accountUrl, AccountResponse.class);
        String puuid = account.getPuuid();

        // 2. puuid 를 통해 matchV5 에서 최근 랭크 게임 20 게임 가져오기
        String matchesUrl = RIOT_MATCHES_API.replace("{puuid}", puuid);
        List<String> matchIds = restTemplate.getForObject(matchesUrl, List.class);

        // 3. 불러온 경기 데이터로 지표 계산
        for (String matchId : matchIds) {
            String matchUrl = RIOT_MATCH_API.replace("{matchId}", matchId);
            String matchTimelineURL = RIOT_MATCH_TIMELINE_DATA_API.replace("{matchId}", matchId);

        }

        //      1. 팀 평균 데스보다 많은가?                 -> isOverAverageDeath()
        //      2. 중후반에 많이 죽냐? (기준은 일단 5데스)     -> doesPlayerDieMidToLateOften()
        //      3. 안하던 챔프를 최근에 하냐                 -> doesPlayUnskilledChampRecently()
        boolean overAverageDeath = isOverAverageDeath();
        boolean diesMidToLateOften = doesPlayerDieMidToLateOften();
        boolean playsUnskilledChamps = doesPlayUnskilledChampRecently();


        // 4. 반환
        return HashtagResponse.of(overAverageDeath, diesMidToLateOften, playsUnskilledChamps);
    }

    private boolean doesPlayUnskilledChampRecently() {
        return true;
    }

    private boolean doesPlayerDieMidToLateOften() {
        return true;
    }

    private boolean isOverAverageDeath() {
        return true;
    }


}
