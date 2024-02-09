package com.example.lostaf.lvaultweb.exceptions;

import io.micrometer.common.lang.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DuplicatedException extends RuntimeException {
    @Nullable
    private final String customMessage;
}
