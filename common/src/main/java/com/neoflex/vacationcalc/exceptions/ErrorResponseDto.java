package com.neoflex.vacationcalc.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder(toBuilder = true)
public class ErrorResponseDto {
    private OffsetDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}