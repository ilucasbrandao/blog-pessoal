package com.dev.blog_pessoal.mapper;

import com.dev.blog_pessoal.dto.PostDTO;
import com.dev.blog_pessoal.dto.PostRequestDTO;
import com.dev.blog_pessoal.model.PostModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Component
public class PostMapper {

    public PostModel toPostModel(PostDTO postDTO){
        PostModel postModel = new PostModel();

        // postModel.setId(postDTO.getId());
        postModel.setTitle(postDTO.getTitle());
        postModel.setDescription(postDTO.getDescription());
        postModel.setDataCriacao(postDTO.getDataCriacao());
        postModel.setCategory(postDTO.getCategory());

        return postModel;
    }

    public PostDTO toPostDTO(PostModel postModel){
        PostDTO postDTO = new PostDTO();

        postDTO.setId(postModel.getId());
        postDTO.setTitle(postModel.getTitle());
        postDTO.setDescription(postModel.getDescription());
        postDTO.setDataCriacao(postModel.getDataCriacao());
        postDTO.setCategory(postModel.getCategory());

        return postDTO;
    }
}
