package com.example.demo.service;

import com.example.demo.config.*;
import com.fasterxml.jackson.core.*;
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
public class RiotApiServiceImpl implements RiotApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final RiotApiConfig riotApiConfig;
    private final String ACCEPT = "Accept";

    public String fetchPuuid(String gameName, String tagLine) {
        String url = riotApiConfig.getAccountUrl()
                .replace("{gameName}", gameName)
                .replace("{tagLine}", tagLine)
                .replace("{apiKey}", riotApiConfig.getKey());
        HttpHeaders headers = new HttpHeaders();
        headers.add(ACCEPT, "*/*");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return parsePuuidJson(response.getBody());
    }

    public List<String> fetchMatchIds(String puuid) {
        String url = riotApiConfig.getMatchesUrl()
                .replace("{puuid}", puuid)
                .replace("{apiKey}", riotApiConfig.getKey());
        HttpHeaders headers = new HttpHeaders();
        headers.add(ACCEPT, "*/*");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return parseMatchIdsJson(response.getBody());
    }

    public boolean isOverAverageDeath(String puuid, List<String> matchIds) {
        int overAverageDeathCount = 0;

        for (String matchId : matchIds) {
            String matchUrl = riotApiConfig.getMatchUrl()
                    .replace("{matchId}", matchId)
                    .replace("{apiKey}", riotApiConfig.getKey());
            ResponseEntity<String> matchResponse = performGetRequest(matchUrl);
            try {
                overAverageDeathCount = getOverAverageDeathCount(puuid, overAverageDeathCount, matchResponse);
            } catch (IOException e) {
                log.error("Error processing match data", e);
            }
        }

        return overAverageDeathCount > matchIds.size() / 2;
    }


    /**
     * 사용자가 숙련되지 않은 챔피언을 사용했는지 검사합니다.
     *
     * @param puuid    사용자의 고유 ID
     * @param matchIds 검사할 매치 ID 목록
     * @return 숙련되지 않은 챔피언을 사용했다면 true, 그렇지 않다면 false
     */
    public boolean isPlayingUnskilledChamps(String puuid, List<String> matchIds) {
        Set<Integer> skilledChampions = getSkilledChampions(puuid);
        return matchIds.stream().anyMatch(matchId -> isUnskilledChampionPlayed(matchId, puuid, skilledChampions));
    }

    /**
     * 사용자의 숙련된 챔피언 목록을 가져옵니다.
     *
     * @param puuid 사용자의 고유 ID
     * @return 사용자의 숙련된 챔피언 ID 집합
     */
    private Set<Integer> getSkilledChampions(String puuid) {
        String masteryUrl = riotApiConfig.getMasteryUrl()
                .replace("{puuid}", puuid)
                .replace("{apiKey}", riotApiConfig.getKey());
        try {
            ResponseEntity<String> masteryResponse = performGetRequest(masteryUrl);
            return parseSkilledChampions(masteryResponse.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

    /**
     * 주어진 URL에 대해 GET 요청을 수행합니다.
     *
     * @param url 요청할 URL
     * @return 서버로부터의 응답
     */
    private ResponseEntity<String> performGetRequest(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ACCEPT, "*/*");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    /**
     * 숙련된 챔피언 정보를 포함하는 JSON 문자열을 파싱합니다.
     *
     * @param json 파싱할 JSON 문자열
     * @return 숙련된 챔피언의 ID를 포함하는 집합
     * @throws IOException 파싱 중 발생한 예외
     */
    private Set<Integer> parseSkilledChampions(String json) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        Set<Integer> skilledChampions = new HashSet<>();
        for (JsonNode championNode : rootNode) {
            int championLevel = championNode.path("championLevel").asInt();
            if (championLevel >= 5) {
                skilledChampions.add(championNode.path("championId").asInt());
            }
        }
        return skilledChampions;
    }

    /**
     * 특정 매치에서 사용자가 숙련되지 않은 챔피언을 사용했는지 검사합니다.
     *
     * @param matchId          검사할 매치 ID
     * @param puuid            사용자의 고유 ID
     * @param skilledChampions 사용자의 숙련된 챔피언 ID 집합
     * @return 숙련되지 않은 챔피언을 사용했다면 true, 그렇지 않다면 false
     */
    private boolean isUnskilledChampionPlayed(String matchId, String puuid, Set<Integer> skilledChampions) {
        String matchUrl = riotApiConfig.getMatchUrl()
                .replace("{matchId}", matchId)
                .replace("{apiKey}", riotApiConfig.getKey());
        try {
            ResponseEntity<String> matchResponse = performGetRequest(matchUrl);
            JsonNode rootNode = objectMapper.readTree(matchResponse.getBody());
            JsonNode participants = rootNode.path("info").path("participants");
            return checkParticipant(puuid, skilledChampions, participants);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 매치 참가자 중 특정 사용자를 찾아 그가 숙련된 챔피언을 사용했는지 검사합니다.
     *
     * @param puuid            사용자의 고유 ID
     * @param skilledChampions 사용자의 숙련된 챔피언 ID 집합
     * @param participants     매치 참가자 정보를 담은 JSON 노드
     * @return 숙련되지 않은 챔피언을 사용했다면 true, 그렇지 않다면 false
     */
    private boolean checkParticipant(String puuid, Set<Integer> skilledChampions, JsonNode participants) {
        for (JsonNode participant : participants) {
            String participantPuuid = participant.path("puuid").asText();
            if (puuid.equals(participantPuuid)) {
                int playedChampId = participant.path("championId").asInt();
                return !skilledChampions.contains(playedChampId);
            }
        }
        return false;
    }

    private int getOverAverageDeathCount(String puuid, int overAverageDeathCount, ResponseEntity<String> matchResponse) throws JsonProcessingException {
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
        return overAverageDeathCount;
    }

    private String parsePuuidJson(String json) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            return rootNode.path("puuid").asText();
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON for PUUID", e);
        }
    }

    private List<String> parseMatchIdsJson(String json) {
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
            throw new RuntimeException("Failed to parse JSON for match IDs", e);
        }
    }
}
