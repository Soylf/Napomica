package com.example.demo.client;

import com.example.demo.client.dto.MessageDtoOutput;
import com.example.demo.client.model.Message;
import com.example.demo.service.MessageService.MessageService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/service")
public class MessageController {
    private final MessageService service;

    @GetMapping("/getMessage")
    public MessageDtoOutput getUserMessage(@RequestParam(name = "chatId") @Positive Long chatId,
                                           @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(name = "size", defaultValue = "15") @Positive Integer size) {
        return service.getUser(chatId, from, size);
    }

    @GetMapping("/getInfoUsers")
    public List<Message> getInfoUsers() {
        return service.getMessage();
    }
}