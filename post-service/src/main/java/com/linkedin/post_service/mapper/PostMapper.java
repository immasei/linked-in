package com.linkedin.post_service.mapper;

import com.linkedin.post_service.dto.CreatePostDTO;
import com.linkedin.post_service.dto.PostDTO;
import com.linkedin.post_service.entity.Post;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final ModelMapper modelMapper;

    public Post toEntity(CreatePostDTO dto) {
        return modelMapper.map(dto, Post.class);
    }

    public PostDTO toDTO(Post post) {
        return modelMapper.map(post, PostDTO.class);
    }

    public List<PostDTO> toDTOs(List<Post> posts) {
        return posts.stream().map(this::toDTO).collect(Collectors.toList());
    }

}