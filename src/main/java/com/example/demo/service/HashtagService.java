package com.example.demo.service;


import com.example.demo.domain.*;

public interface HashtagService {

    HashtagResponse getPlayerHashtags(String gameName, String tagLine);
}
