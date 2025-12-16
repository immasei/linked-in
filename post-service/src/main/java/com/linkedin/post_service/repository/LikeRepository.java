package com.linkedin.post_service.repository;

import com.linkedin.post_service.entity.Like;
import com.linkedin.post_service.enums.LikeTargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserIdAndTargetTypeAndTargetId(Long userId,
                                                   LikeTargetType targetType,
                                                   Long targetId);

    @Transactional
    void deleteByUserIdAndTargetTypeAndTargetId(Long userId,
                                                LikeTargetType targetType,
                                                Long targetId);

    long countByTargetTypeAndTargetId(
            LikeTargetType targetType,
            Long targetId
    );
}
