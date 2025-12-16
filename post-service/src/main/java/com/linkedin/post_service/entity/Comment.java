package com.linkedin.post_service.entity;

import com.linkedin.post_service.enums.LikeTargetType;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(
    name = "comments",
    indexes = {
        @Index(name = "idx_comments_post_id_created_at", columnList = "post_id, created_at"),
        @Index(name = "idx_comments_user_id", columnList = "user_id"),
        @Index(name = "idx_comments_parent_id", columnList = "parent_id")
    }
)
public class Comment implements LikeTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String content;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private Post post;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean deleted = false;

    // null = top-level comment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private Comment parent;

    // direct children, not grandchilren
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Comment> replies = new ArrayList<>();

    public void softDelete() {
        this.deleted = true;
    }

    public void addReply(Comment reply) {
        reply.setParent(this);
        reply.setPost(this.post);
        this.replies.add(reply);
    }

    @Override
    public Long getCreatorId() {
        return userId;
    }

    @Override
    public LikeTargetType getTargetType() {
        return LikeTargetType.COMMENT;
    }
}

