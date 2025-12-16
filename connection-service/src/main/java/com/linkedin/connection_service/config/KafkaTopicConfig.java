package com.linkedin.connection_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic sendConnectionRequest() {
        return TopicBuilder.name(KafkaTopics.SEND_CONNECTION_REQUEST)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic acceptConnectionRequest() {
        return TopicBuilder.name(KafkaTopics.ACCEPT_CONNECTION_REQUEST)
                .partitions(3)
                .replicas(1)
                .build();
    }

}
