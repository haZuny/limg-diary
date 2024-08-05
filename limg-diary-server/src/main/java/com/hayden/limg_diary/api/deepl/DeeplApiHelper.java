package com.hayden.limg_diary.api.deepl;


import com.hayden.limg_diary.api.deepl.dto.DeeplResponseForm;
import com.hayden.limg_diary.api.deepl.dto.DeeplTransitionForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.*;

@Component
public class DeeplApiHelper {
    @Value("${deepl.url}")
    String url;

    @Value("${deepl.auth}")
    String auth;

    public Optional<DeeplResponseForm> post(String text){
        WebClient webClient = WebClient.builder().baseUrl(url).build();

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("target_lang", "EN");
        bodyMap.put("text", Arrays.asList(text));

        try{
            return Optional.ofNullable(
                    webClient.post()
                            .header("Authorization", auth)
                            .header("Content-Type", "application/json")
                            .bodyValue(bodyMap)
                            .retrieve()
                            .bodyToMono(DeeplResponseForm.class)
                            .block()
            );
        } catch (Exception e){
            return Optional.empty();
        }
    }

    DeeplTransitionForm getFirstTransition(DeeplResponseForm responseForm){
        return responseForm.getTranslations().getFirst();
    }

    String getText(DeeplTransitionForm transitionForm){
        return transitionForm.getText();
    }

    public Optional<String> transrate(String text){
        try{
            Optional<DeeplResponseForm> responseForm = post(text);
            DeeplTransitionForm transitionForm = getFirstTransition(responseForm.get());
            String transitionText = getText(transitionForm);
            return Optional.of(transitionText);
        } catch (Exception e){
            return Optional.empty();
        }
    }
}