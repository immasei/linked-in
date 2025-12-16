package com.linkedin.notification_service.event;

import com.linkedin.notification_service.enums.AggregateType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCreated {

    private final Long creatorId;
    private final String content;
    private final Long postId;
    private final AggregateType aggregateType;

}
