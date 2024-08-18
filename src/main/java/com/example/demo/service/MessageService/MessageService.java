package com.example.demo.service.MessageService;

import com.example.demo.client.dto.MessageDto;
import com.example.demo.client.dto.MessageDtoOutput;
import com.example.demo.client.model.Message;

import java.util.List;

public interface MessageService {

    void add(MessageDto saysInBotDto);

    MessageDtoOutput getUser(Long chatId, Integer from, Integer size);

    List<Message> getMessage();
}
