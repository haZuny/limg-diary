package com.hayden.limg_diary.api;

import com.hayden.limg_diary.api.api_dto.KarloImageForm;
import com.hayden.limg_diary.api.api_dto.KarloResponseForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class KarloApiHelper {
    @Value("${karlo.url}")
    String url;

    @Value("${karlo.auth}")
    String auth;

    public Optional<KarloResponseForm> post(String prompt) {
        WebClient webClient = WebClient.builder().baseUrl(url).build();

        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("version", "v2.1");
        bodyMap.put("prompt", prompt);
        bodyMap.put("width", "1024");
        bodyMap.put("height", "1024");

        try {
            return Optional.ofNullable(
                    webClient.post()
                            .header("Authorization", auth)
                            .header("Content-Type", "application/json")
                            .bodyValue(bodyMap)
                            .retrieve()
                            .bodyToMono(KarloResponseForm.class)
                            .block()
            );
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    KarloImageForm getFirstImage(KarloResponseForm responseForm) {
        return responseForm.getImages().getFirst();
    }

    String getImageUrl(KarloImageForm imageForm) {
        return imageForm.getImage();
    }

    public void saveImageUrl(String imgUrl, String savePath) throws IOException {
        InputStream inputStream = new URL(imgUrl).openStream();
        Files.copy(inputStream, Path.of(savePath));
    }
}
