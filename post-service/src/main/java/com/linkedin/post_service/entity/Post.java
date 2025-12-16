package com.linkedin.post_service.entity;

import com.linkedin.post_service.enums.LikeTargetType;
import com.linkedin.post_service.enums.PostVisibility;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(
    name = "posts",
    indexes = {
        @Index(name = "idx_posts_user_id", columnList = "user_id"),
        @Index(name = "idx_posts_created_at", columnList = "created_at"),
        @Index(name = "idx_posts_visibility", columnList = "visibility"),
        @Index(name = "idx_posts_user_visibility_created", columnList = "user_id, visibility, created_at")
    }
)
public class Post implements LikeTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostVisibility visibility = PostVisibility.PUBLIC;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Override
    public Long getCreatorId() {
        return userId;
    }

    @Override
    public LikeTargetType getTargetType() {
        return LikeTargetType.POST;
    }
}

