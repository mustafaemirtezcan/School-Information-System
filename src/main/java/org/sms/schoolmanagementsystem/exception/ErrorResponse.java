package org.sms.schoolmanagementsystem.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private LocalDateTime timestamp;
    private int status;

    public ErrorResponse(String message, LocalDateTime timestamp, int status) {
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
    }
}
