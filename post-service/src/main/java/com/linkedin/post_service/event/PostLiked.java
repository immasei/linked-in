package com.linkedin.post_service.event;

import com.linkedin.post_service.entity.Like;
import com.linkedin.post_service.enums.AggregateType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString
public class PostLiked extends LikedEvent {

    private final Long postId;

    @Builder.Default
    private final AggregateType aggregateType = AggregateType.POST;

    @Override
    public Long getAggregateId() {
        return postId;
    }

    public static PostLiked of(Like like, Long creatorId) {
        return PostLiked.builder()
                .postId(like.getTargetId())
                .likedByUserId(like.getUserId())
                .creatorId(creatorId)
                .build();
    }

}