package com.linkedin.notification_service.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendConnectionRequest {

    private final Long senderId;
    private final Long receiverId;

}
