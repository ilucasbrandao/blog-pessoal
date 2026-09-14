package com.dev.blog_pessoal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Long id;
    private @NotBlank String title;
    private @NotBlank String description;
    private OffsetDateTime dataCriacao;
    private @NotBlank String category;
}
