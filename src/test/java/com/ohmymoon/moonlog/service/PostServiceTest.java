package com.ohmymoon.moonlog.service;

import com.ohmymoon.moonlog.domain.Post;
import com.ohmymoon.moonlog.repository.PostRepository;
import com.ohmymoon.moonlog.request.PostCreate;
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
        postService.write(request);

        // then
        Assertions.assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
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
        assertEquals(1L, postRepository.count());
        assertEquals("foo", response.getTitle());
        assertEquals("bar", response.getContent());
    }

    @Test
    @DisplayName("글 목록 조회")
    void getList() {
        // given
        Post requestPost1 = Post.builder()
                .title("foo1")
                .content("bar1")
                .build();
        postRepository.save(requestPost1);

        Post requestPost2 = Post.builder()
                .title("foo2")
                .content("bar2")
                .build();
        postRepository.save(requestPost2);

        // when
        List<PostResponse> response = postService.getList();

        // then
        assertNotNull(response);
        assertEquals(2L, response.size());

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

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "id");

        // when
        List<PostResponse> response = postService.getList(pageable);

        // then
        assertNotNull(response);
        assertEquals(10L, response.size());
        assertEquals("제목 - 30", response.get(0).getTitle());

    }
}