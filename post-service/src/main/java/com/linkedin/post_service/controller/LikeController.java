package com.linkedin.post_service.controller;


import com.linkedin.post_service.dto.LikeRequestDTO;
import com.linkedin.post_service.entity.Like;
import com.linkedin.post_service.mapper.LikeMapper;
import com.linkedin.post_service.mapper.PostMapper;
import com.linkedin.post_service.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final LikeMapper likeMapper;

    @PostMapping
    public ResponseEntity<Void> like(@RequestBody LikeRequestDTO dto) {
        Like like = likeMapper.toEntity(dto);
        like.setUserId(1L);

        likeService.like(like);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unlike(@RequestBody LikeRequestDTO dto) {
        likeService.unlike(1L, dto.getTargetType(), dto.getTargetId());
        return ResponseEntity.noContent().build();
    }
//
//    @GetMapping
//    public ResponseEntity<List<LikeDTO>> getLikes(@RequestParam LikeTargetType targetType,
//                                                  @RequestParam Long targetId) {
//        return ResponseEntity.ok(likeService.getLikes(targetType, targetId));
//    }
}