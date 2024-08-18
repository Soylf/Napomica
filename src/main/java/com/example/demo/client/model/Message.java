package com.example.demo.client.model;

import jakarta.persistence.*;
import lombok.*;

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
}