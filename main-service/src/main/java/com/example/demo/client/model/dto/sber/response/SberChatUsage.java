package com.example.demo.client.model.dto.sber.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SberChatUsage {
    private int completion_tokens;
    private int prompt_tokens;
    private int system_tokens;
    private int total_tokens;
}