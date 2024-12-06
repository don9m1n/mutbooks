package com.ll.mutbooks.domain.post.repository;

import com.ll.mutbooks.domain.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}