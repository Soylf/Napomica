package com.example.demo.service.messageService;

import com.example.demo.client.model.dto.MessageDto;
import com.example.demo.client.model.dto.MessageDtoOutput;
import com.example.demo.client.mapper.MessageMapper;
import com.example.demo.client.model.Message;
import com.example.demo.client.model.MessageTexts;
import com.example.demo.client.model.BotMessageTexts;
import com.example.demo.client.repository.BotMessageTextsRepository;
import com.example.demo.client.repository.MessageRepository;
import com.example.demo.client.repository.MessageTextsRepository;
import com.example.demo.error.exception.NotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository repository;
    private final MessageTextsRepository textsRepository;
    private final BotMessageTextsRepository botTextsRepository;

    @Override
    @Transactional
    public void add(@NotNull MessageDto messageDto) {
        Message message = MessageMapper.MAPPER.fromDto(messageDto);
        message.setName(messageDto.getName());

        repository.save(message);
        saveUserText(messageDto, message.getChatId());
        saveBotText(messageDto, message.getChatId());
    }

    @Override
    public MessageDtoOutput getUser(Long chatId, Integer from, Integer size) {
        CheckUserById(chatId);
        return MessageDtoOutput.builder()
                .chatId(chatId)
                .name("User ID: " + chatId)
                .text(getTextPage(chatId, from, size))
                .textBot(getTextAiPage(chatId, from, size))
                .build();
    }

    @Override
    public List<Message> getMessage() {
        return repository.findAll();
    }


    private void CheckUserById(Long chatId) {
        repository.findById(chatId)
                .orElseThrow(() -> new NotFoundException("Not Found"));
    }

    private List<BotMessageTexts> getTextAiPage(Long chatId,Integer from, Integer size) { //checkText - TextAi or TextUser
        Pageable pageable = PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "id"));
        return botTextsRepository.findAllByChatId(chatId, pageable).getContent();
    }
    private List<MessageTexts> getTextPage(Long chatId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "id"));
        return textsRepository.findAllByChatId(chatId, pageable).getContent();
    }

    private void saveUserText(@NotNull MessageDto messageDto, Long chatId) {
        MessageTexts newText = new MessageTexts();
        newText.setText(messageDto.getText());
        newText.setChatId(chatId);
        textsRepository.save(newText);
    }
    private void saveBotText(@NotNull MessageDto messageDto, Long chatId) {
        BotMessageTexts newBotText = new BotMessageTexts();
        newBotText.setText(messageDto.getTextBot());
        newBotText.setChatId(chatId);
        botTextsRepository.save(newBotText);
    }
}