package com.portfolio.datamapper;

import org.springframework.stereotype.Component;

import com.portfolio.dto.auth.UserResponseDTO;
import com.portfolio.entity.User;

/**
 * Mapper for User entity <-> DTO conversions.
 */
@Component
public class UserMapper {

    /**
     * Converts User entity to UserResponseDTO (without sensitive data).
     *
     * @param user the user entity
     * @return the user response DTO
     */
    public UserResponseDTO toResponseDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
