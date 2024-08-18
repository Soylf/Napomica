package com.example.demo.client.dto;

import com.example.demo.client.model.BotMessageTexts;
import com.example.demo.client.model.MessageTexts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDtoOutput {
    private Long chatId;
    private String name;
    private List<MessageTexts> text;
    private List<BotMessageTexts> textBot;
}
