package com.example.lostaf.lvaultweb.models;

import org.springframework.http.HttpStatus;

import io.micrometer.common.lang.Nullable;
import jakarta.annotation.Nonnull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Result<E> {
    @Nonnull
    private boolean isSuccess;
    
    @Nonnull
    private HttpStatus statusCode;

    @Nullable
    private String message;

    @Nullable
    private E data;
}
