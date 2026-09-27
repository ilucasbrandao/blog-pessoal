package com.dev.blog_pessoal.controller;

import com.dev.blog_pessoal.dto.PostDTO;
import com.dev.blog_pessoal.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public String saudacoes(){
        return "Bem vindo a primeira rota do servidor.";
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAll(@PageableDefault(size = 10) Pageable pageable){
        Page<PostDTO> postagens = service.getAll(pageable);
        return ResponseEntity.ok(postagens.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getById(@PathVariable Long id){
        PostDTO postById = service.getById(id);
        return ResponseEntity.ok(postById);
    }

    @PostMapping
    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostDTO postagem, Authentication authentication){
        String userEmail = authentication.getName();
        PostDTO postCriado = service.create(postagem, userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(postCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> update(@PathVariable Long id, @Valid @RequestBody PostDTO postagemAtualizada) {
        PostDTO postAtualizado = service.update(id, postagemAtualizada);
        return ResponseEntity.ok(postAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}