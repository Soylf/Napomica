package com.example.demo.service.saysInBotService;

import com.example.demo.client.dto.MessageDto;
import com.example.demo.client.dto.MessageDtoOutput;

public interface MessageService {

    void add(MessageDto saysInBotDto);

    void deleteUser(Integer chatId);

    MessageDtoOutput getUser(Integer chatId, Integer from, Integer size);
}
