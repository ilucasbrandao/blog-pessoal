package com.dev.blog_pessoal.mapper;

import com.dev.blog_pessoal.dto.PostRequestDTO;
import com.dev.blog_pessoal.model.PostModel;

import java.time.LocalDateTime;

public class PostMapper {
    public static PostModel toEntity(PostRequestDTO requestDTO){
        PostModel model = new PostModel();
        model.setTitle(requestDTO.title());
        model.setDescription(requestDTO.description());
        model.setCategory(requestDTO.category());
        model.setDataCriacao(LocalDateTime.now());

        return model;
    }
}
