package com.ohmymoon.moonlog.repository;

import com.ohmymoon.moonlog.domain.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(Pageable pageable);
}
