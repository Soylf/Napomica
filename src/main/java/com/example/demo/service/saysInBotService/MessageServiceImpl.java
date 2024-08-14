package com.example.demo.service.saysInBotService;


import com.example.demo.client.dto.MessageDto;
import com.example.demo.client.dto.MessageDtoOutput;
import com.example.demo.client.mapper.MessageMapper;
import com.example.demo.client.model.Message;
import com.example.demo.client.model.MessageTexts;
import com.example.demo.client.model.BotMessageTexts;
import com.example.demo.client.repository.BotMessageTextsRepository;
import com.example.demo.client.repository.MessageRepository;
import com.example.demo.client.repository.MessageTextsRepository;
import com.example.demo.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository repository;
    private final MessageTextsRepository textsRepository;
    private final BotMessageTextsRepository botTextsRepository;

    @Override
    @Transactional
    public void add(MessageDto messageDto) {
        Message message = MessageMapper.MAPPER.fromDto(messageDto);
        if (!Objects.equals(message.getName(), messageDto.getName())) {
            message.setName(messageDto.getName());
        }
        if (messageDto.getText() != null) {
            MessageTexts newText = new MessageTexts();
            newText.setText(messageDto.getText());
            newText.setChatId(message.getChatId());
            textsRepository.save(newText);
        }
        if (messageDto.getTextBot() != null) {
            BotMessageTexts newBotText = new BotMessageTexts();
            newBotText.setText(messageDto.getTextBot());
            newBotText.setChatId(message.getChatId());
            botTextsRepository.save(newBotText);
        }
        repository.save(message);
    }

    @Override
    @Transactional
    public void deleteUser(Integer chatId) {
        repository.delete(GetUserById(chatId));
    }

    @Override
    public MessageDtoOutput getUser(Integer chatId, Integer size, Integer from) {
        CheckUserById(chatId);
        return MessageDtoOutput.builder()
                .chatId(chatId)
                .name("User ID: " + chatId)
                .text(getTextAndTextAiPage(chatId,false, from, size))
                .textBot(getTextAndTextAiPage(chatId,true, from, size))
                .build();
    }

    //dop-methods
    private Message GetUserById(Integer chatId) {
        return repository.findById(chatId)
                .orElseThrow(() -> new NotFoundException("Not Found"));
    }
    private void CheckUserById(Integer chatId) {
        repository.findById(chatId)
                .orElseThrow(() -> new NotFoundException("Not Found"));
    }


    private Page<String> getTextAndTextAiPage(Integer chatId, boolean checkText, Integer from, Integer size) { //checkText - TextAi or TextUser
        Pageable pageable = PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "id"));
        if(checkText) {
            return botTextsRepository.findAllByChatId(chatId, pageable); //textAi
        } else {
            return textsRepository.findAllByChatId(chatId, pageable); //text
        }
    }
}