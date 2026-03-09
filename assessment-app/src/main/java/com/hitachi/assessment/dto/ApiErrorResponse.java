package com.hitachi.assessment.dto;

public record ApiErrorResponse(int status,
        String message,
        String field) {
}
