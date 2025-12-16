package com.linkedin.connection_service.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AcceptConnectionRequest {

    private final Long senderId;
    private final Long receiverId;

    public static AcceptConnectionRequest of(Long senderId, Long receiverId) {
        return AcceptConnectionRequest.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
        }

}
