package com.linkedin.post_service.controller;

import com.linkedin.post_service.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //    @PostMapping
//    public ResponseEntity<CommentDTO> addComment(@PathVariable Long postId,
//                                                 @RequestBody CreateCommentRequest request,
//                                                 @AuthenticationPrincipal UserPrincipal user) { ... }
//
//    @GetMapping
//    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable Long postId) { ... }

//    @PutMapping("/{commentId}")
//    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long commentId, ...) { ... }

//    @DeleteMapping("/{commentId}")
//    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) { ... }





}
