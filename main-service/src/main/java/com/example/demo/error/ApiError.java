package com.example.demo.error;

import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private String status;
    private String reason;
    private String message;
    private String timeStamp = getFormatDate(new Date());

    private static String getFormatDate(Date data) {
        return new SimpleDateFormat("HH:mm dd MMM yyyy").format(data);
    }
}
