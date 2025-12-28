package com.example.demo.client.model.dto.sber;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SberMessagesDto {
    private String role;
    private String content;
}
