package com.linkedin.post_service.service;

import com.linkedin.post_service.entity.Post;
import com.linkedin.post_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postsRepository;

    @Transactional
    public Post createPost(Post post) {
        return postsRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long postId) {
        return postsRepository.findByIdOrThrow(postId);
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPostsOfUser(Long userId) {
        return postsRepository.findByUserId(userId);
    }

}

