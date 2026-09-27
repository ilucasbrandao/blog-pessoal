package com.dev.blog_pessoal.service;

import com.dev.blog_pessoal.dto.PostDTO;
import com.dev.blog_pessoal.exception.ResourceNotFoundException;
import com.dev.blog_pessoal.mapper.PostMapper;
import com.dev.blog_pessoal.model.PostModel;
import com.dev.blog_pessoal.model.User;
import com.dev.blog_pessoal.repository.PostRepository;
import com.dev.blog_pessoal.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository repository;
    private final UserRepository userRepository;
    private final PostMapper mapper;

    public PostService(PostRepository repository, UserRepository userRepository, PostMapper mapper){
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    // CREATE
    public PostDTO create(PostDTO postagem, String userEmail){
        User autor = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        PostModel postCriado = mapper.toPostModel(postagem, autor);
        postCriado = repository.save(postCriado);

        return mapper.toPostDTO(postCriado);
    }

    // READ
    public Page<PostDTO> getAll(Pageable pageable) {
        Page<PostModel> postagens = repository.findAll(pageable);
        return postagens.map(mapper::toPostDTO);
    }

    // READ BY ID
    public PostDTO getById(Long id) {
        PostModel postEncontrado = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return mapper.toPostDTO(postEncontrado);
    }

    // UPDATE: Atualiza os dados diretamente no registro recuperado do banco
    public PostDTO update(Long id, PostDTO postDTO) {
        PostModel postEncontrado = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        // Atualiza apenas as propriedades alteráveis
        postEncontrado.setTitle(postDTO.getTitle());
        postEncontrado.setDescription(postDTO.getDescription());
        postEncontrado.setCategory(postDTO.getCategory());

        PostModel postSalvo = repository.save(postEncontrado);

        return mapper.toPostDTO(postSalvo);
    }

    // DELETE
    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }
        repository.deleteById(id);
    }
}