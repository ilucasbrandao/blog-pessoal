package com.dev.blog_pessoal.controller;

import com.dev.blog_pessoal.dto.PostDTO;
import com.dev.blog_pessoal.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<PostDTO>> getAll(){
        List<PostDTO> postagens = service.getAll();
        return ResponseEntity.ok(postagens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getById(@PathVariable Long id){
        PostDTO postById = service.getById(id);
        return ResponseEntity.ok(postById);
    }

    @PostMapping("/criar")
    public ResponseEntity<PostDTO> create(@RequestBody PostDTO postagem){
        PostDTO postCriado = service.create(postagem);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postCriado);
    }

    @PutMapping("/atualizar/{id}")
    public PostDTO update(@PathVariable Long id, @RequestBody PostDTO postagemAtualizada){
        return service.update(id, postagemAtualizada);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        if(service.getById(id) != null){
            service.delete(id);
            return ResponseEntity.ok("Deletado com sucesso!");
        } else {
            return ResponseEntity.ok("Postagem excluida com sucesso");
        }
    }
}
