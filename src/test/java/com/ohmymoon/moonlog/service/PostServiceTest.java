package com.ohmymoon.moonlog.service;

import com.ohmymoon.moonlog.domain.Post;
import com.ohmymoon.moonlog.exception.PostNotFoundException;
import com.ohmymoon.moonlog.repository.PostRepository;
import com.ohmymoon.moonlog.request.PostCreate;
import com.ohmymoon.moonlog.request.PostEdit;
import com.ohmymoon.moonlog.request.PostSearch;
import com.ohmymoon.moonlog.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("글 작성")
    void write() {
        // given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();


        // when
        Post post = postService.write(request);

        // then

        assertTrue(post.getId() > 0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void getOne() {
        // given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(requestPost);

        // when
        PostResponse response = postService.get(requestPost.getId());

        // then
        assertNotNull(response);
        assertEquals("foo", response.getTitle());
        assertEquals("bar", response.getContent());
    }

    @Test
    @DisplayName("존재하지 않는 글 조회")
    void getNone() {
        // given
        Post post = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(post);

        // expected
        PostNotFoundException e = assertThrows(PostNotFoundException.class, () -> {
            postService.get(post.getId() + 1);
        });

        assertEquals(PostNotFoundException.MESSAGE, e.getMessage());
    }


    @Test
    @DisplayName("글 페이지 조회")
    void getPageList() {
        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                        .mapToObj(i -> {
                            return Post.builder()
                                    .title("제목 - " + i)
                                    .content("내용 - " + i)
                                    .build();
                        })
                        .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        PostSearch search = PostSearch.builder()
                .page(1)
                .size(10)
                .build();

        // when
        List<PostResponse> response = postService.getList(search);

        // then
        assertNotNull(response);
        assertEquals(10L, response.size());
        assertEquals("제목 - 30", response.get(0).getTitle());

    }

    @Test
    @DisplayName("글 제목 수정")
    void edit() {
        // given
        Post post = Post.builder()
                        .title("테스트 제목")
                                .content("테스트 내용")
                                        .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .build();

        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다."));
        assertNotNull(changedPost);
        assertEquals(postEdit.getTitle(), changedPost.getTitle());
        assertEquals(postEdit.getContent(), changedPost.getContent());

    }

    @Test
    @DisplayName("게시글 삭제")
    void delete() {
        // given
        Post post = Post.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .build();

        postRepository.save(post);

        long postId = post.getId();

        PostEdit postEdit = PostEdit.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .build();

        // when
        postService.delete(postId);

        // then
        assertEquals(null, postRepository.findById(postId).orElse(null));
    }
}