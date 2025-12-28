package com.example.demo.service.messageService;

import com.example.demo.client.model.dto.MessageDto;
import com.example.demo.client.model.dto.MessageDtoOutput;
import com.example.demo.client.model.Message;

import java.util.List;

public interface MessageService {

    void add(MessageDto saysInBotDto);

    MessageDtoOutput getUser(Long chatId, Integer from, Integer size);

    List<Message> getMessage();
}
