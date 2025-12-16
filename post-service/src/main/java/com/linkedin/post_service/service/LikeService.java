package com.linkedin.post_service.service;

import com.linkedin.post_service.entity.Like;
import com.linkedin.post_service.entity.LikeTarget;
import com.linkedin.post_service.enums.LikeTargetType;
import com.linkedin.post_service.event.Event;
import com.linkedin.post_service.event.LikedEvent;
import com.linkedin.post_service.event.PostLiked;
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
public class  LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final OutboxService outboxService;

    @Transactional
    public void like(Like like) {
        LikeTarget target = findTarget(like.getTargetType(), like.getTargetId());

        if (likeRepository.existsByUserIdAndTargetTypeAndTargetId(
                like.getUserId(), like.getTargetType(), like.getTargetId()
        )) throw new BadRequestException("Already liked.");

        Like savedLike = likeRepository.save(like);

        LikedEvent evt = createLikedEvent(savedLike, target);
        outboxService.save(evt);

        log.info("[Post] Liked: userId={}, type={}, id={}",
                like.getUserId(), like.getTargetType(), like.getTargetType());

    }

    @Transactional
    public void unlike(Long userId, LikeTargetType targetType, Long targetId) {
        findTarget(targetType, targetId);

        if (!likeRepository.existsByUserIdAndTargetTypeAndTargetId(
                userId, targetType, targetId
        )) throw new BadRequestException("Not liked before.");

        likeRepository.deleteByUserIdAndTargetTypeAndTargetId(userId, targetType, targetId);

        log.info("[Post] Unliked: userId={}, type={}, id={}",
                userId, targetType, targetId);
    }

    @Transactional(readOnly = true)
    public long getLikes(LikeTargetType targetType, Long targetId) {
        findTarget(targetType, targetId);
        return likeRepository.countByTargetTypeAndTargetId(targetType, targetId);
    }

    private LikeTarget findTarget(LikeTargetType targetType, Long targetId) {
        return switch (targetType) {
            case POST -> postRepository.findByIdOrThrow(targetId);
            default -> throw new BadRequestException("Unsupported target: " + targetType);
        };
    }

    private LikedEvent createLikedEvent(Like like, LikeTarget target) {
        return switch (target.getTargetType()) {
            case POST -> PostLiked.of(like, target.getCreatorId());
            default -> throw new BadRequestException("Unsupported target: " + target.getTargetType());
        };
    }

}
