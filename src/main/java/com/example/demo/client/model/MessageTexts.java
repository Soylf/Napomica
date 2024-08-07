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
@Table(name = "MessageTexts")
public class MessageTexts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long textId;
    @ManyToOne
    @JoinColumn(name = "chatId")
    private Message message;
    private String text;
}