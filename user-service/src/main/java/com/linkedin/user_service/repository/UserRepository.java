package com.linkedin.user_service.repository;

import com.linkedin.user_service.entity.User;
import com.linkedin.user_service.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    default User findByEmailOrThrow(String email) {
        return findByEmail(email).orElseThrow(() ->
                    new ResourceNotFoundException("User not found with email: " + email));
    }

    boolean existsByEmail(String email);
}

