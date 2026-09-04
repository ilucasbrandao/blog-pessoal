package com.dev.blog_pessoal.controller;

import com.dev.blog_pessoal.model.PostModel;
import com.dev.blog_pessoal.repository.PostRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @GetMapping
    public String saudacoes(){
        return "Bem vindo a primeira rota do servidor.";
    }

}
