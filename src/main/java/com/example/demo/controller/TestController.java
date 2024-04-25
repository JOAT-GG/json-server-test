package com.example.demo.controller;

import com.example.demo.domain.RateResponse;
import com.example.demo.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final RateService rateService;

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
}
