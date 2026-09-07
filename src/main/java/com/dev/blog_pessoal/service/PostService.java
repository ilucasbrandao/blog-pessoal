package com.dev.blog_pessoal.service;

import com.dev.blog_pessoal.model.PostModel;
import com.dev.blog_pessoal.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository repository;

    public PostService(PostRepository repository){this.repository = repository;}

    // CREATE
    public PostModel create(PostModel postagem){
        return repository.save(postagem);
    }

    // READ
    public List<PostModel> getAll(){
        return repository.findAll();
    }

    // READ BY ID
    public Optional<PostModel> getById(Long id) {
        return repository.findById(id);
    }

    // UPDATE
    public PostModel update(Long id, PostModel postAtualizado){
       Optional<PostModel> postEncontrado = repository.findById(id);
       postEncontrado.map(p -> {
           p.setTitle(postAtualizado.getTitle());
           p.setDescription(postAtualizado.getDescription());
           p.setDataCriacao(postAtualizado.getDataCriacao());
           p.setCategory(postAtualizado.getCategory());
           return repository.save(p);
       });
       return null;
    }

    // DELETE
    public void delete(Long id){
        repository.deleteById(id);
    }

}
