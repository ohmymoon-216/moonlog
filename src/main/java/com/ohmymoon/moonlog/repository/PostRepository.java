package com.ohmymoon.moonlog.repository;

import com.ohmymoon.moonlog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
