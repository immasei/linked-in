package com.linkedin.post_service.repository;

import com.linkedin.post_service.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // top-level comments under a post
//    List<Comment> findByPostIdAndParentCommentIdIsNull(Long postId);
//
//    // replies under a comment
//    List<Comment> findByParentCommentId(Long parentCommentId);
}
