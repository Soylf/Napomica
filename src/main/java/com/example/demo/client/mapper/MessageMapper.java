package com.example.demo.client.mapper;

import com.example.demo.client.dto.MessageDto;
import com.example.demo.client.model.Message;
import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface MessageMapper {
    MessageMapper MAPPER = Mappers.getMapper(MessageMapper.class);

    MessageDto toDto(Message message);

    default Message fromDto(MessageDto saysInBotDto) {
        Message message = new Message();
        message.setChatId(saysInBotDto.getChatId());
        message.setName(saysInBotDto.getName());

        if (saysInBotDto.getText() != null) {
            message.getMessageTexts().add(saysInBotDto.getText());
        }
        if (saysInBotDto.getTextBot() != null) {
            message.getBotMessageTexts().add(saysInBotDto.getTextBot());
        }

        return message;
    }

    List<MessageDto> toDtos(List<Message> messages);
}