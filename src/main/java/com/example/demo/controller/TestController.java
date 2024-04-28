package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final RateService rateService;
    private final HashtagService hashtagService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {

        return ResponseEntity.ok("Hello JOAT.GG!");
    }

    @GetMapping("/api/v1/matchResult")
    public ResponseEntity<RateResponse> getMatchResult() {
        return ResponseEntity.ok(
                rateService.getRateData()
        );
    }

    @GetMapping("/api/v1/hashTags")
    public ResponseEntity<HashtagResponse> getPlayerHashTags(@RequestParam String gameName, @RequestParam String tagLine) {
        return ResponseEntity.ok(
                hashtagService.getPlayerHashtags(gameName, tagLine)
        );
    }
}
