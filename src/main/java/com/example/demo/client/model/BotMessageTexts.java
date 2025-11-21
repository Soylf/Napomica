package com.example.demo.client.model;

import jakarta.persistence.*;
import lombok.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "BotMessageTexts")
public class BotMessageTexts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long chatId;
    private String text;
    private String dateTime =
            String.valueOf(LocalDateTime.parse(new SimpleDateFormat("HH:mm dd MMM yyyy").format(new Date())));
}