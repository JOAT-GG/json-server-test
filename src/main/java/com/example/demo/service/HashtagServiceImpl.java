package com.example.demo.service;

import com.example.demo.domain.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final RiotApiService riotApiService;

    @Override
    public HashtagResponse getPlayerHashtags(String gameName, String tagLine) {
        String puuid = riotApiService.fetchPuuid(gameName, tagLine);
        List<String> matchIds = riotApiService.fetchMatchIds(puuid);
        boolean overAverageDeath = riotApiService.isOverAverageDeath(puuid, matchIds);
        boolean diesMidToLateOften = false;  // Placeholder for additional logic
        boolean playsUnskilledChamps = riotApiService.isPlayingUnskilledChamps(puuid, matchIds);

        return HashtagResponse.of(overAverageDeath, diesMidToLateOften, playsUnskilledChamps);
    }

}