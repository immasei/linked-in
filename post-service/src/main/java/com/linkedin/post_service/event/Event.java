package com.linkedin.post_service.event;

import com.linkedin.post_service.enums.AggregateType;

public interface Event {

    Long getAggregateId();
    AggregateType getAggregateType();

}