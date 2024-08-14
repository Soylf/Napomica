package com.example.demo.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDtoOutput {
    private Integer chatId;
    private String name;
    private Page<String> text;
    private Page<String> textBot;
}
