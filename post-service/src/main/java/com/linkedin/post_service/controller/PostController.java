package com.linkedin.post_service.controller;

import com.linkedin.post_service.auth.UserContextHolder;
import com.linkedin.post_service.client.ConnectionClient;
import com.linkedin.post_service.dto.CreatePostDTO;
import com.linkedin.post_service.dto.PersonDTO;
import com.linkedin.post_service.dto.PostDTO;
import com.linkedin.post_service.entity.Post;
import com.linkedin.post_service.mapper.PostMapper;
import com.linkedin.post_service.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final ConnectionClient c;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody CreatePostDTO dto) {
        Long userId = UserContextHolder.getCurrentUserId();
        Post post = postMapper.toEntity(dto);
        post.setUserId(userId);

        List<PersonDTO> pp = c.getFirstConnections(userId);

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

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<PostDTO>> getAllPostsOfUser(@PathVariable Long userId) {
        List<Post> posts = postService.getAllPostsOfUser(userId);
        return ResponseEntity.ok(postMapper.toDTOs(posts));
    }

}