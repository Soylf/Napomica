package com.example.demo.client.model.dto.sber.response;

import com.example.demo.client.model.dto.sber.SberMessagesDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SberChatChoice {
    private String finish_reason;
    private int index;
    private SberMessagesDto message;
}
