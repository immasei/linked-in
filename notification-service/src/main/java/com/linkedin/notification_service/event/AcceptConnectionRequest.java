package com.linkedin.notification_service.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AcceptConnectionRequest {

    private final Long senderId;
    private final Long receiverId;

}
