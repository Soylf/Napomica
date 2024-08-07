package com.example.demo.client.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "BotMessageTexts")
public class BotMessageTexts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long textAiId;
    @ManyToOne
    @JoinColumn(name = "chatId")
    private Message message;
    private String text;
}