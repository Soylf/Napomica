package com.example.demo.client.model.dto.sber.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SberChatResponseDto {
    private List<SberChatChoice> choices;
    private long created;
    private String model;
    private String object;
    private SberChatUsage usage;
}
