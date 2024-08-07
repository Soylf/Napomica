package com.example.demo.service.saysInBotService;

import com.example.demo.client.dto.MessageDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageService {
    @Transactional
    void add(MessageDto saysInBotDto);

    void update(MessageDto saysInBotDto);

    List<String> getUsersByChatId(long chatId);

    MessageDto getUser(long chatId);

    void deleteUser(long chatId);
}
