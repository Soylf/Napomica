package com.example.demo.client.repository;

import com.example.demo.client.model.MessageTexts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTextsRepository extends JpaRepository<MessageTexts, Long> {
    Page<MessageTexts> findAllByChatId(Long chatId, Pageable pageable);
}
