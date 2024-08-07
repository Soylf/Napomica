package com.example.demo.client.mapper;

import com.example.demo.client.dto.MessageDto;
import com.example.demo.client.model.Message;
import com.example.demo.client.model.MessageTexts;
import com.example.demo.client.model.BotMessageTexts;
import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface MessageMapper {
    MessageMapper MAPPER = Mappers.getMapper(MessageMapper.class);

    MessageDto toDto(Message message);

    default Message fromDto(MessageDto saysInBotDto) {
        Message message = new Message();
        message.setChatId(saysInBotDto.getChatId()); // Обновил имя метода для соответствия полю
        message.setName(saysInBotDto.getName());

        if (saysInBotDto.getText() != null) {
            MessageTexts text = new MessageTexts();
            text.setMessage(message);
            text.setText(saysInBotDto.getText());
            message.getMessageTexts().add(text);
        }
        if (saysInBotDto.getTextBot() != null) {
            BotMessageTexts textBot = new BotMessageTexts();
            textBot.setMessage(message);
            textBot.setText(saysInBotDto.getTextBot());
            message.getBotMessageTexts().add(textBot);
        }

        return message;
    }

    List<MessageDto> toDtos(List<Message> messages);
}