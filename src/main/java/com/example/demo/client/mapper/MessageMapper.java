package com.example.demo.client.mapper;

import com.example.demo.client.dto.MessageDto;
import com.example.demo.client.model.Message;
import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {
    MessageMapper MAPPER = Mappers.getMapper(MessageMapper.class);

    MessageDto toDto(Message message);

    Message fromDto(MessageDto messageDto);
}