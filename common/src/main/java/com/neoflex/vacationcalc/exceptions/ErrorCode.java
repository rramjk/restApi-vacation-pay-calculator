package com.neoflex.vacationcalc.exceptions;

public enum ErrorCode {
    PARAMETER_OF_REQUEST_IS_MISSING("Обязательный параметр '%s' не указан"),
    PARAMETER_OF_REQUEST_IS_INCORRECT("Параметр '%s' указан некорректно"),
    PARAMETER_START_DATE_OR_END_DATE_IS_INCORRECT("Значения начала или конца отпуска указаны некорректно")
    ;

    private final String errorMessage;

    ErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(Object... args) {
        return String.format(errorMessage, args);
    }
}
