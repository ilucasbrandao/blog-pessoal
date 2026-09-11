package com.dev.blog_pessoal.service;

import com.dev.blog_pessoal.dto.PostDTO;
import com.dev.blog_pessoal.mapper.PostMapper;
import com.dev.blog_pessoal.model.PostModel;
import com.dev.blog_pessoal.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository repository;
    private final PostMapper mapper;

    public PostService(PostRepository repository, PostMapper mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    // CREATE
    public PostDTO create(PostDTO postagem){
        PostModel postCriado = mapper.toPostModel(postagem);
        postCriado = repository.save(postCriado);
        return mapper.toPostDTO(postCriado);
    }

    // READ
    public List<PostDTO> getAll(){
        List<PostModel> postagens = repository.findAll();
        return postagens.stream()
                .map(mapper::toPostDTO)
                .collect(Collectors.toList());
    }

    // READ BY ID
    public Optional<PostDTO> getById(Long id) {
        Optional<PostModel> postEncontrado = repository.findById(id);
        return postEncontrado.map(mapper::toPostDTO);
    }

    // UPDATE
    public PostDTO update(Long id, PostDTO postDTO){
       Optional<PostModel> postEncontrado = repository.findById(id);

       if(postEncontrado.isPresent()){
           PostModel postAtualizado = mapper.toPostModel(postDTO);
           postAtualizado.setId(id);
           PostModel postSalvo = repository.save(postAtualizado);
           return mapper.toPostDTO(postSalvo);
       } else {
           return null;
       }
    }

    // DELETE
    public void delete(Long id){
        repository.deleteById(id);
    }

}
