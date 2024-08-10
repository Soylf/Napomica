package com.example.demo.client.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Messages")
public class Message {
    @Id
    @Column(name = "chat_id")
    private Long chatId;
    private String name;
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MessageTexts> messageTexts = new ArrayList<>();
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BotMessageTexts> botMessageTexts = new ArrayList<>();
}