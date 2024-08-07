package com.example.demo.client;

import com.example.demo.client.dto.MessageDto;
import com.example.demo.service.saysInBotService.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("Test")
public class MessageController {
    private MessageService service;

    @GetMapping("/user/{chatId}") // Вывести все что сказал пользователь и что ему ответили
    public List<String> getTextUsers(@PathVariable long chatId) {
        return service.getUsersByChatId(chatId);
    }

    @GetMapping("/details/{chatId}") // Получить детали пользователя
    public MessageDto getUser(@PathVariable long chatId){
        return service.getUser(chatId);
    }

    @DeleteMapping("/{chatId}") // Удалить пользователя
    public ResponseEntity<Void> banedUser(@PathVariable long chatId) {
        service.deleteUser(chatId);
        return ResponseEntity.ok().build();
    }
}
