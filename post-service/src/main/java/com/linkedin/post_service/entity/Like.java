package com.linkedin.post_service.entity;

import com.linkedin.post_service.enums.LikeTargetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(
    name = "likes",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_likes_user_target", columnNames = {"user_id", "target_id", "target_type"})
    },
    indexes = {
        @Index(name = "idx_likes_target", columnList = "target_type, target_id"),
        @Index(name = "idx_likes_user", columnList = "user_id")
    }
)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long targetId;

    @Enumerated(EnumType.STRING)
    private LikeTargetType targetType;

    @CreationTimestamp
    private LocalDateTime createdAt;

}

