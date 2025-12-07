package com.linkedin.user_service.service;

import com.linkedin.user_service.dto.LoginRequestDTO;
import com.linkedin.user_service.dto.SignupRequestDTO;
import com.linkedin.user_service.dto.UserDTO;
import com.linkedin.user_service.entity.User;
import com.linkedin.user_service.exception.BadRequestException;
import com.linkedin.user_service.exception.ResourceNotFoundException;
import com.linkedin.user_service.repository.UserRepository;
import com.linkedin.user_service.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserDTO signUp(SignupRequestDTO dto) {
        // soft pre-check (fast failure)
        boolean exists = userRepository.existsByEmail(dto.getEmail());
        if(exists) throw new BadRequestException("User already exists.");

        User user = modelMapper.map(dto, User.class);
        String hashedPassword = PasswordUtil.hashPassword(dto.getPassword());
        user.setPassword(hashedPassword);

        // final safety-net (db constraint)
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public String login(LoginRequestDTO dto) {
        User user = userRepository.findByEmailOrThrow(dto.getEmail());

        boolean isPasswordMatch = PasswordUtil
                .checkPassword(dto.getPassword(), user.getPassword());
        if(!isPasswordMatch) throw new BadRequestException("Incorrect password");

        return jwtService.generateAccessToken(user);
    }
}
