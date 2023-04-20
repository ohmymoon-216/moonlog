package com.ohmymoon.moonlog.controller;

import com.ohmymoon.moonlog.domain.Post;
import com.ohmymoon.moonlog.request.PostCreate;
import com.ohmymoon.moonlog.request.PostEdit;
import com.ohmymoon.moonlog.request.PostSearch;
import com.ohmymoon.moonlog.response.PostResponse;
import com.ohmymoon.moonlog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시글 저장
     * @param request
     * @return
     */
    @PostMapping("/posts")
    public ResponseEntity post(@RequestBody @Valid PostCreate request)  {
        log.info("글작성 - 제목: {}, 내용: {}", request.getTitle(), request.getContent());
        postService.write(request);

        return ResponseEntity.ok(null);
    }

    /**
     * 단건 조회
     * @param postId
     * @return
     */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId){
      return postService.get(postId);
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch search) {
        return postService.getList(search);
    }

    @PatchMapping("posts/{postId}")
    public PostResponse edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request){
        return postService.edit(postId, request);
    }

    @DeleteMapping("posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}
