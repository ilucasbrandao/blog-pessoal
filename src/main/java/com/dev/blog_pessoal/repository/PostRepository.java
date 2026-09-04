package com.dev.blog_pessoal.repository;

import com.dev.blog_pessoal.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository <PostModel, Long> {
}
