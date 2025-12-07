package com.linkedin.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDTO {

    @Email(message = "Invalid email format.")
    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Password must not be blank.")
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String password;

}
