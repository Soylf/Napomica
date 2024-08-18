package com.example.demo.client.repository;

import com.example.demo.client.model.BotMessageTexts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotMessageTextsRepository extends JpaRepository<BotMessageTexts, Long> {
    Page<BotMessageTexts> findAllByChatId(Long chatId, Pageable pageable);
}
