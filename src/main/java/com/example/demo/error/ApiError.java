package com.example.demo.error;

import lombok.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private String status;
    private String reason;
    private String message;
    private LocalDateTime timeStamp =
            LocalDateTime.parse(new SimpleDateFormat("HH:mm dd MMM yyyy").format(new Date()));
}
