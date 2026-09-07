package com.dev.blog_pessoal.dto;

import java.time.OffsetDateTime;

public record PostResponseDTO(Long id,
                              String title,
                              String description,
                              OffsetDateTime dataCriacao,
                              String category) {
}
