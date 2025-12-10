package com.linkedin.post_service.mapper;

import com.linkedin.post_service.config.GlobalMapperConfig;
import com.linkedin.post_service.dto.LikeRequestDTO;
import com.linkedin.post_service.entity.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class)
public interface LikeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Like toEntity(LikeRequestDTO dto);

    LikeRequestDTO toDTO(Like like);
}
