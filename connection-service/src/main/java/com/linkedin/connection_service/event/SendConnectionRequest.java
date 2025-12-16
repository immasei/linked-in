package com.linkedin.connection_service.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendConnectionRequest {

    private final Long senderId;
    private final Long receiverId;

    public static SendConnectionRequest of(Long senderId, Long receiverId) {
        return SendConnectionRequest.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
    }

}
