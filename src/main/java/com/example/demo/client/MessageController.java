package com.example.demo.client;

import com.example.demo.client.dto.MessageDtoOutput;
import com.example.demo.service.saysInBotService.MessageService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("Test")
public class MessageController {
    private final MessageService service;

    @GetMapping("{chatId}") // Получить детали пользователя
    public MessageDtoOutput getUser(@PathVariable Integer chatId,
                                    @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                    @RequestParam(name = "size", defaultValue = "10") @Positive Integer size){
        return service.getUser(chatId, from, size);
    }

    @DeleteMapping("/{chatId}") // Удалить пользователя
    public ResponseEntity<Void> banedUser(@PathVariable Integer chatId) {
        service.deleteUser(chatId);
        return ResponseEntity.ok().build();
    }
}
