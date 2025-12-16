package com.linkedin.post_service.event;

import com.linkedin.post_service.entity.Post;
import com.linkedin.post_service.enums.AggregateType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class PostCreated implements Event {

    private final Long creatorId;
    private final String content;
    private final Long postId;

    @Builder.Default
    private final AggregateType aggregateType = AggregateType.POST;

    @Override
    public Long getAggregateId() {
        return postId;
    }

    public static PostCreated of(Post post) {
        return PostCreated.builder()
                .postId(post.getId())
                .creatorId(post.getUserId())
                .content(post.getContent())
                .build();
    }

}
