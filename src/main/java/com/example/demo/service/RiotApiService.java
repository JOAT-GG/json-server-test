package com.example.demo.service;

import java.util.*;

public interface RiotApiService {

    String fetchPuuid(String gameName, String tagLine);

    List<String> fetchMatchIds(String puuid);

    public boolean isPlayingUnskilledChamps(String puuid, List<String> matchIds);

    public boolean isOverAverageDeath(String puuid, List<String> matchIds);
}
