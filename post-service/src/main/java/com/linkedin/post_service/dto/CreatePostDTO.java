package com.linkedin.post_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.linkedin.post_service.enums.PostVisibility;
import com.linkedin.post_service.validation.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePostDTO {

    @NotBlank(message = "content must not be null.")
    private String content;

    @ValidEnum(enumClass = PostVisibility.class, message = "Invalid visibility level provided.")
    private PostVisibility visibility;

}
