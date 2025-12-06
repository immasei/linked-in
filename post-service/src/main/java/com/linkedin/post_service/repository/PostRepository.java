package com.linkedin.post_service.repository;

import com.linkedin.post_service.entity.Post;
import com.linkedin.post_service.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);

    default Post findByIdOrThrow(Long postId) {
        return findById(postId).orElseThrow(() ->
            new ResourceNotFoundException("Post not found with id: " + postId));
    }

}
