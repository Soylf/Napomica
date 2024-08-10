package com.example.demo.service.saysInBotService;


import com.example.demo.client.dto.MessageDto;
import com.example.demo.client.mapper.MessageMapper;
import com.example.demo.client.model.Message;
import com.example.demo.client.model.MessageTexts;
import com.example.demo.client.model.BotMessageTexts;
import com.example.demo.client.repository.MessageRepository;
import com.example.demo.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository repository;

    @Override
    @Transactional
    public void add(MessageDto messageDto) {
        repository.save(MessageMapper.MAPPER.fromDto(messageDto));
    }

    @Override
    @Transactional
    public void update(MessageDto messageDto) {
        Message foundMessage = checkAndGetUserById(messageDto.getChatId());
        if (!Objects.equals(foundMessage.getName(), messageDto.getName())) {
            foundMessage.setName(messageDto.getName());
        }
        if (messageDto.getText() != null) {
            MessageTexts newText = new MessageTexts();
            newText.setMessage(foundMessage);
            newText.setText(messageDto.getText());
            foundMessage.getMessageTexts().add(newText);

        }
        if (messageDto.getTextBot() != null) {
            BotMessageTexts newBotText = new BotMessageTexts();
            newBotText.setMessage(foundMessage);
            newBotText.setText(messageDto.getTextBot());
            foundMessage.getBotMessageTexts().add(newBotText);
        }
        repository.save(foundMessage);
    }

    @Override
    @Transactional
    public void deleteUser(long chatId) {
        repository.delete(checkAndGetUserById(chatId));
    }

    @Override
    public List<String> getUsersByChatId(long chatId) {
        return checkAndGetUserById(chatId)
                .getMessageTexts()
                .stream()
                .map(MessageTexts::getText)
                .collect(Collectors.toList());
    }

    @Override
    public MessageDto getUser(long chatId) {
        return MessageMapper.MAPPER.toDto(checkAndGetUserById(chatId));
    }

    private Message checkAndGetUserById(long chatId) {
        return repository.findById(chatId)
                .orElseThrow(() -> new NotFoundException("Not Found"));
    }
}