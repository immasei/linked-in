package com.linkedin.post_service.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class LikedEvent implements Event {

    protected final Long creatorId;
    protected final Long likedByUserId;

}
