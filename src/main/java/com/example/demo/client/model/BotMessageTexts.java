package com.example.demo.client.model;

import jakarta.persistence.*;
import lombok.*;

import java.text.SimpleDateFormat;
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
    private String dateTime = getFormatDate(new Date());

    private static String getFormatDate(Date data) {
        return new SimpleDateFormat("HH:mm dd MMM yyyy").format(data);
    }
}