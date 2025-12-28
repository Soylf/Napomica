package com.example.demo.config;

import com.example.demo.service.sberAiService.SberAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CreateTokenScheduled {
    private final SberAiService service;

    public CreateTokenScheduled(SberAiService service) {
        this.service = service;
    }

    @Scheduled(fixedRate = 30*60*1000) // 30 min
    public void refresh() {
        log.info("Токен-доступа переобновился");
        service.generateToken();
    }
}
