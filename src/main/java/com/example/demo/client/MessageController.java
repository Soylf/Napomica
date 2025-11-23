package com.example.demo.client;

import com.example.demo.client.model.dto.MessageDtoOutput;
import com.example.demo.client.model.Message;
import com.example.demo.service.messageService.MessageService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message/service")
public class MessageController {
    private final MessageService service;

    @GetMapping("/getMessage")
    public MessageDtoOutput getUserMessage(@RequestParam(name = "chatId") @Positive Long chatId,
                                           @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(name = "size", defaultValue = "6") @Positive Integer size) {
        return service.getUser(chatId, from, size);
    }

    @GetMapping("/getInfoUsers")
    public List<Message> getInfoUsers() {
        return service.getMessage();
    }
}