package com.dev.blog_pessoal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Long id;
    private String title;
    private String description;
    private OffsetDateTime dataCriacao;
    private String category;
}
