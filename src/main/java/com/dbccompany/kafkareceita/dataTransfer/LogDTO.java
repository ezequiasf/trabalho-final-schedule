package com.dbccompany.kafkareceita.dataTransfer;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogDTO {
    private String text;
    private TypeLog type;
    private LocalDateTime operationDate;

    public LogDTO constroiLog(String text, TypeLog type) {
        return LogDTO.builder().text(text)
                .type(type)
                .operationDate(LocalDateTime.now())
                .build();
    }
}
