package com.linkedin.notification_service.consumer;

import com.linkedin.notification_service.client.ConnectionClient;
import com.linkedin.notification_service.dto.PersonDTO;
import com.linkedin.notification_service.event.PostCreated;
import com.linkedin.notification_service.event.PostLiked;
import com.linkedin.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostConsumer {

    private final ConnectionClient c;
    private final NotificationService notificationService;

    @KafkaListener(topics = "outbox.event.post-created")
    public void handlePostCreated(PostCreated evt) {
        log.info("[Notification] handlePostCreated: {}", evt);
        List<PersonDTO> connections = c.getFirstConnections(evt.getCreatorId());

        for(PersonDTO conn: connections) {
            notificationService.send(conn.getUserId(),
                "Your connection, @" + evt.getCreatorId() +" has created" + " a post, Check it out~");
        }
    }

    @KafkaListener(topics = "outbox.event.post-liked")
    public void handlePostLiked(PostLiked evt) {
        log.info("[Notification] handlePostLiked: {}", evt);
        String message = String.format(
                "Your post, %d has been liked by @%d", evt.getPostId(), evt.getLikedByUserId());

        notificationService.send(evt.getCreatorId(), message);
    }

}
