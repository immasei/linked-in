package com.linkedin.post_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.linkedin.post_service.enums.LikeTargetType;
import com.linkedin.post_service.validation.ValidEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikeRequestDTO {

    @NotNull(message = "targetId must not be null.")
    private Long targetId;

    @NotNull(message = "targetType must not be null.")
    @ValidEnum(enumClass = LikeTargetType.class, message = "Invalid target type provided.")
    private LikeTargetType targetType;

}
