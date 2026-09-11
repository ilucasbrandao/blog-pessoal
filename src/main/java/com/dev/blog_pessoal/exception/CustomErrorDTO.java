package com.dev.blog_pessoal.exception;

import java.time.OffsetDateTime;

public record CustomErrorDTO(
        OffsetDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path
) {}
