package com.ohmymoon.moonlog.service;

import com.ohmymoon.moonlog.domain.Post;
import com.ohmymoon.moonlog.domain.PostEditor;
import com.ohmymoon.moonlog.exception.PostNotFoundException;
import com.ohmymoon.moonlog.repository.PostRepository;
import com.ohmymoon.moonlog.request.PostCreate;
import com.ohmymoon.moonlog.request.PostEdit;
import com.ohmymoon.moonlog.request.PostSearch;
import com.ohmymoon.moonlog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post write(PostCreate postCreate) {

        Post post = Post.builder()
                    .title(postCreate.getTitle())
                    .content(postCreate.getContent())
                    .build();

        return postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException());

        return new PostResponse(post.getId(), post.getTitle(), post.getContent());

    }

    public List<PostResponse> getList() {
        return postRepository.findAll().stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getList(PostSearch search) {
        return postRepository.getList(search).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponse edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException());

        PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();

        PostEditor editor = postEditorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(editor);

        return new PostResponse(post.getId(), post.getTitle(), post.getContent());
    }

    public void delete(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException());
        postRepository.delete(post);
    }
}
