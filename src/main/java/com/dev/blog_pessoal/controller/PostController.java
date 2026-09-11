package com.dev.blog_pessoal.controller;

import com.dev.blog_pessoal.dto.PostDTO;
import com.dev.blog_pessoal.model.PostModel;
import com.dev.blog_pessoal.repository.PostRepository;
import com.dev.blog_pessoal.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {

    private PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public String saudacoes(){
        return "Bem vindo a primeira rota do servidor.";
    }

    @GetMapping("/listar")
    public List<PostDTO> getAll(){
        return service.getAll();
    }

    @GetMapping("/listar/{id}")
    public Optional<PostDTO> getById(@PathVariable Long id){
        return service.getById(id);
    }

    @PostMapping("/criar")
    public PostDTO create(@RequestBody PostDTO postagem){
        return service.create(postagem);
    }

    @PutMapping("/atualizar/{id}")
    public PostDTO update(@PathVariable Long id, @RequestBody PostDTO postagemAtualizada){
        return service.update(id, postagemAtualizada);
    }

    @DeleteMapping("/deletar/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}
