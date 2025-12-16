package com.linkedin.notification_service.event;

import com.linkedin.notification_service.enums.AggregateType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostLiked {

    private final Long creatorId;
    private final Long postId;
    private final Long likedByUserId;
    private final AggregateType aggregateType;

}