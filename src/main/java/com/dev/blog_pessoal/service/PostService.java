package com.dev.blog_pessoal.service;

import com.dev.blog_pessoal.model.PostModel;
import com.dev.blog_pessoal.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private PostRepository repository;

    public PostService(PostRepository repository){this.repository = repository;}

    public PostModel create(PostModel postagem){
        return repository.save(postagem);
    }

    public List<PostModel> getAll(){
        return repository.findAll();
    }

}
