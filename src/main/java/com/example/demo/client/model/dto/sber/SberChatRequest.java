package com.example.demo.client.model.dto.sber;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SberChatRequest {
    private String model;
    private List<SberMessagesDto> messages;
    private boolean stream;
    private double repetition_penalty;
}