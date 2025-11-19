package com.example.demo.service.sberAiService;

import com.example.demo.client.model.dto.sber.SberChatRequest;
import com.example.demo.client.model.dto.sber.response.SberChatResponseDto;
import com.example.demo.client.model.dto.sber.SberMessagesDto;
import com.example.demo.config.SberModelConfig;
import com.example.demo.error.exception.BadRequestException;
import com.example.demo.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SberAiServiceImpl implements SberAiService{
    private final RestTemplate restTemplate;
    private String sberAccessToken;
    private final SberModelConfig sberModelConfig;


    @Override
    public void generateToken() {
        HttpEntity<?> entity = requestToken();
        ResponseEntity<Map> response = restTemplate.exchange(
                sberModelConfig.getTokenUrl(),
                HttpMethod.POST,
                entity,
                Map.class
        );

        if(response.getStatusCode().is2xxSuccessful()) {
            if(response.getBody() != null) {
                sberAccessToken = response.getBody().get("access_token").toString();
            } else {
                throw new NotFoundException("Ответ не содержит body");
            }
        }else {
            throw new BadRequestException("Что-то пошло не так: " + response.getStatusCode());
        }
    }

    @Override
    public String call(String text) {
        HttpEntity<?> entity = requestText(text);
        ResponseEntity<SberChatResponseDto> response = restTemplate.exchange(
                sberModelConfig.getUrlChat(),
                HttpMethod.POST,
                entity,
                SberChatResponseDto.class
        );

        if(response.getStatusCode().is2xxSuccessful()) {
            if(response.getBody() != null) {
                return response.getBody().getChoices().get(0).getMessage().getContent();
            } else {
                throw new NotFoundException("Ответ не содержит body");
            }
        }else {
            throw new BadRequestException("Что-то пошло не так: " + response.getStatusCode());
        }
    }

    private HttpEntity<?> requestToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("RqUID", sberModelConfig.getRqUID());
        headers.set("Authorization", "Basic " + sberModelConfig.getAuthorizationKey());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("scope", "GIGACHAT_API_PERS");

        return new HttpEntity<>(body, headers);
    }

    private HttpEntity<?> requestText(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(sberAccessToken);

        SberChatRequest body = new SberChatRequest(
                sberModelConfig.getModel(),
                List.of(new SberMessagesDto("user", text)),
                false,
                1.0
        );

        return new HttpEntity<>(body, headers);
    }

}