package com.hayden.limg_diary.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class KarloApiHelper {
    @Value("${karlo.url}")
    String url;

    @Value("${karlo.auth}")
    String auth;

    public KarloResponseForm post(String prompt){
        WebClient webClient = WebClient.builder().baseUrl(url).build();

        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("version", "v2.1");
        bodyMap.put("prompt", prompt);
        bodyMap.put("width", "1024");
        bodyMap.put("height", "1024");

        return webClient.post()
                .header("Authorization", auth)
                .header("Content-Type", "application/json")
                .bodyValue(bodyMap)
                .retrieve()
                .bodyToMono(KarloResponseForm.class)
                .block();
    }
}
