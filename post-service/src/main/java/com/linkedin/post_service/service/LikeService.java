package com.linkedin.post_service.service;

import com.linkedin.post_service.entity.Like;
import com.linkedin.post_service.enums.LikeTargetType;
import com.linkedin.post_service.exception.BadRequestException;
import com.linkedin.post_service.repository.LikeRepository;
import com.linkedin.post_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Transactional
    public void like(Like like) {
        checkTargetExists(like.getTargetType(), like.getTargetId());

        if (likeRepository.existsByUserIdAndTargetTypeAndTargetId(
                like.getUserId(), like.getTargetType(), like.getTargetId()
        )) throw new BadRequestException("Already liked.");

        likeRepository.save(like);
    }

    @Transactional
    public void unlike(Long userId, LikeTargetType targetType, Long targetId) {
        checkTargetExists(targetType, targetId);

        if (!likeRepository.existsByUserIdAndTargetTypeAndTargetId(
                userId, targetType, targetId
        )) throw new BadRequestException("Not liked before.");

        likeRepository
            .deleteByUserIdAndTargetTypeAndTargetId(userId, targetType, targetId);
    }

    private void checkTargetExists(LikeTargetType targetType, Long targetId) {
        switch (targetType) {
            case POST -> postRepository.findByIdOrThrow(targetId);
//            case COMMENT -> commentRepository.findByIdOrThrow(targetId);
//            case REPLY -> replyRepository.findByIdOrThrow(targetId);
            default -> throw new BadRequestException("Unsupported like target type: " + targetType);
        }
    }


}
