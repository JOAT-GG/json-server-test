package com.example.demo.config;

import lombok.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "riot.api")
public class RiotApiConfig {
    private String key;
    private String accountUrl;
    private String matchesUrl;
    private String matchUrl;
    private String masteryUrl;
}
