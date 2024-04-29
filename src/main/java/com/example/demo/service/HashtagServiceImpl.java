package com.example.demo.service;

import com.example.demo.domain.*;
import com.fasterxml.jackson.databind.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.io.*;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String API_KEY = "RGAPI-ce054f05-d3b8-4b29-9532-819b83a22364";
    private static final String RIOT_ACCOUNT_API = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/{summonerName}/{tagLine}?api_key=" + API_KEY;
    private static final String RIOT_MATCHES_API = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/{puuid}/ids?type=ranked&start=0&count=20&api_key=" + API_KEY;
    private static final String RIOT_MATCH_API = "https://asia.api.riotgames.com/lol/match/v5/matches/{matchId}?api_key=" + API_KEY;
    private static final String RIOT_CHAMPION_MASTERY_API = "https://kr.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-puuid/{puuid}?api_key=" + API_KEY;

    @Override
    public HashtagResponse getPlayerHashtags(String gameName, String tagLine) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "*/*");  // 모든 타입의 콘텐츠를 받아들임
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String accountUrl = RIOT_ACCOUNT_API.replace("{summonerName}", gameName).replace("{tagLine}", tagLine);
        ResponseEntity<String> accountResponse = restTemplate.exchange(accountUrl, HttpMethod.GET, entity, String.class);
        String puuid = parsePuuid(accountResponse.getBody());

        String matchesUrl = RIOT_MATCHES_API.replace("{puuid}", puuid);
        ResponseEntity<String> matchesResponse = restTemplate.exchange(matchesUrl, HttpMethod.GET, entity, String.class);
        List<String> matchIds = parseMatchIds(matchesResponse.getBody());

        boolean overAverageDeath = isOverAverageDeath(puuid, matchIds);
        boolean diesMidToLateOften = false;  // Placeholder
        boolean playsUnskilledChamps = isPlayingUnskilledChamps(puuid, matchIds);  // Placeholder

        return HashtagResponse.of(overAverageDeath, diesMidToLateOften, playsUnskilledChamps);
    }

    private boolean isPlayingUnskilledChamps(String puuid, List<String> matchIds) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "*/*");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        Set<Integer> skilledChampions = new HashSet<>(); // 숙련도가 있는 챔프가 더 적다고 생각
        String masteryUrl = RIOT_CHAMPION_MASTERY_API.replace("{puuid}", puuid);
        ResponseEntity<String> masteryResponse = restTemplate.exchange(masteryUrl, HttpMethod.GET, entity, String.class);
        try {
            JsonNode rootNode = objectMapper.readTree(masteryResponse.getBody());
            for (JsonNode championNode : rootNode) {
                int championLevel = championNode.path("championLevel").asInt();
                if (championLevel >= 5)
                    skilledChampions.add(championNode.path("championId").asInt());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String matchId : matchIds) {
            String matchUrl = RIOT_MATCH_API.replace("{matchId}", matchId);
            ResponseEntity<String> matchResponse = restTemplate.exchange(matchUrl, HttpMethod.GET, entity, String.class);
            try {
                JsonNode rootNode = objectMapper.readTree(matchResponse.getBody());
                JsonNode participants = rootNode.path("info").path("participants");
                for (JsonNode participant : participants) {
                    String participantPuuid = participant.path("puuid").asText(); // 참가자의 PUUID 추출
                    if (puuid.equals(participantPuuid)) { // 파라미터로 받은 PUUID와 일치하는 참가자만 검사
                        int playedChampId = participant.path("championId").asInt();
                        if (!skilledChampions.contains(playedChampId)) {
                            return true; // 숙련되지 않은 챔피언을 사용한 경우
                        }
                        break; // 일치하는 참가자를 찾았으므로 더 이상의 참가자를 검사할 필요 없음
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false; // 검토한 모든 경기에서 숙련된 챔피언만 사용했으면 false 반환
    }

    private String parsePuuid(String json) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            return rootNode.path("puuid").asText();
        } catch (IOException e) {
            log.error("Failed to parse JSON for PUUID", e);
            throw new RuntimeException("Failed to parse JSON for PUUID", e);
        }
    }

    private List<String> parseMatchIds(String json) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            List<String> matchIds = new ArrayList<>();
            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    matchIds.add(node.asText());
                }
            }
            return matchIds;
        } catch (IOException e) {
            log.error("Failed to parse JSON for match IDs", e);
            throw new RuntimeException("Failed to parse JSON for match IDs", e);
        }
    }

    private boolean isOverAverageDeath(String puuid, List<String> matchIds) {
        int overAverageDeathCount = 0;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "*/*");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        for (String matchId : matchIds) {
            String matchUrl = RIOT_MATCH_API.replace("{matchId}", matchId);
            ResponseEntity<String> matchResponse = restTemplate.exchange(matchUrl, HttpMethod.GET, entity, String.class);
            try {
                JsonNode rootNode = objectMapper.readTree(matchResponse.getBody());
                JsonNode participantsNode = rootNode.path("info").path("participants");
                int participantDeaths = 0;
                int totalDeaths = 0;
                int numParticipants = 0;

                for (JsonNode participant : participantsNode) {
                    int deaths = participant.path("deaths").asInt();
                    totalDeaths += deaths;
                    numParticipants++;
                    if (participant.path("puuid").asText().equals(puuid)) {
                        participantDeaths = deaths;
                    }
                }

                if (numParticipants > 0 && participantDeaths > (totalDeaths / numParticipants)) {
                    overAverageDeathCount++;
                }
            } catch (IOException e) {
                log.error("Error processing match data", e);
            }
        }

        return overAverageDeathCount > matchIds.size() / 2;
    }
}