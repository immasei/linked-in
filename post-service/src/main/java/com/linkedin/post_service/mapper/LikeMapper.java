package com.linkedin.post_service.mapper;

import com.linkedin.post_service.dto.LikeRequestDTO;
import com.linkedin.post_service.entity.Like;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeMapper {

    private final ModelMapper modelMapper;

    public Like toEntity(LikeRequestDTO dto) {
        return modelMapper.map(dto, Like.class);
    }

    public LikeRequestDTO toDTO(Like like) {
        return modelMapper.map(like, LikeRequestDTO.class);
    }
}
