package com.ohmymoon.moonlog.service;

import com.ohmymoon.moonlog.domain.Post;
import com.ohmymoon.moonlog.repository.PostRepository;
import com.ohmymoon.moonlog.request.PostCreate;
import com.ohmymoon.moonlog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {

        Post post = Post.builder()
                    .title(postCreate.getTitle())
                    .content(postCreate.getContent())
                    .build();

        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return new PostResponse(post.getId(), post.getTitle(), post.getContent());

    }

    public List<PostResponse> getList() {
        return postRepository.findAll().stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getList(Pageable pageable) {
        return postRepository.getList(pageable).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }
}
