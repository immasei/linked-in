package com.linkedin.post_service.entity;

import com.linkedin.post_service.enums.LikeTargetType;

public interface LikeTarget {
    Long getCreatorId();
    Long getId();
    LikeTargetType getTargetType();
}

