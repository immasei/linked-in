package com.linkedin.post_service.mapper;

import com.linkedin.post_service.config.GlobalMapperConfig;
import com.linkedin.post_service.dto.CreatePostDTO;
import com.linkedin.post_service.dto.PostDTO;
import com.linkedin.post_service.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface PostMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Post toEntity(CreatePostDTO dto);

    PostDTO toDTO(Post post);
    List<PostDTO> toDTOs(List<Post> posts);
}

