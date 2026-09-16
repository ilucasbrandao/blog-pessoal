package com.dev.blog_pessoal.service;

import com.dev.blog_pessoal.dto.PostDTO;
import com.dev.blog_pessoal.exception.ResourceNotFoundException;
import com.dev.blog_pessoal.mapper.PostMapper;
import com.dev.blog_pessoal.model.PostModel;
import com.dev.blog_pessoal.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


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
    public Page<PostDTO> getAll(Pageable pageable) {
        // O findAll(pageable) já busca os dados paginados do banco
        Page<PostModel> postagens = repository.findAll(pageable);
        // O Page do Spring possui um metodo .map()
         return postagens.map(mapper::toPostDTO);
    }

    // READ BY ID
    public PostDTO getById(Long id) {
        PostModel postEncontrado = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return mapper.toPostDTO(postEncontrado);
    }

    // UPDATE
    public PostDTO update(Long id, PostDTO postDTO){
        PostModel postEncontrado = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        PostModel postAtualizado = mapper.toPostModel(postDTO);
        postAtualizado.setId(id);
        postAtualizado = repository.save(postAtualizado);

        return mapper.toPostDTO(postAtualizado);
    }

    // DELETE
    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Post not found with id: " +id);
        }

        repository.deleteById(id);
    }

}
