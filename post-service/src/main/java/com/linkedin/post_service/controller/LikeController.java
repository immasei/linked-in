package com.linkedin.post_service.controller;


import com.linkedin.post_service.auth.UserContextHolder;
import com.linkedin.post_service.dto.LikeRequestDTO;
import com.linkedin.post_service.entity.Like;
import com.linkedin.post_service.enums.LikeTargetType;
import com.linkedin.post_service.mapper.LikeMapper;
import com.linkedin.post_service.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final LikeMapper likeMapper;

    @PostMapping
    public ResponseEntity<Void> like(@RequestBody LikeRequestDTO dto) {
        Long userId = UserContextHolder.getCurrentUserId();
        Like like = likeMapper.toEntity(dto);
        like.setUserId(userId);

        likeService.like(like);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unlike(@RequestBody LikeRequestDTO dto) {
        Long userId = UserContextHolder.getCurrentUserId();
        likeService.unlike(userId, dto.getTargetType(), dto.getTargetId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Long> getLikes(@RequestParam LikeTargetType type, @RequestParam Long id) {
        return ResponseEntity.ok(likeService.getLikes(type, id));
    }
}