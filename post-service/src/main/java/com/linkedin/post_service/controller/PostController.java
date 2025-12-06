package com.linkedin.post_service.controller;

import com.linkedin.post_service.dto.CreatePostDTO;
import com.linkedin.post_service.dto.PostDTO;
import com.linkedin.post_service.entity.Post;
import com.linkedin.post_service.mapper.PostMapper;
import com.linkedin.post_service.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(
        @RequestBody CreatePostDTO dto, HttpServletRequest req
    ) {
        Post post = postMapper.toEntity(dto);
        post.setUserId(1L);

        Post createdPost = postService.createPost(post);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postMapper.toDTO(createdPost));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok(postMapper.toDTO(post));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getAllPostsOfUser(@PathVariable Long userId) {
        List<Post> posts = postService.getAllPostsOfUser(userId);
        return ResponseEntity.ok(postMapper.toDTOs(posts));
    }

}