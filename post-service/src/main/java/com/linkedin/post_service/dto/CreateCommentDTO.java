package com.linkedin.post_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
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
public class CreateCommentDTO {

    @NotBlank(message = "content must not be blank.")
    private String content;

    @NotNull(message = "postId must not be null.")
    private Long postId;

    // if set, create reply; if null, top–level comment
    private Long parentId;

}
