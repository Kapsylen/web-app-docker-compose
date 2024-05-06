package dev.sebsve.application.model;

import dev.sebsve.infrastructue.model.User;
import lombok.Builder;

@Builder
public record UserApi(
        String id,
        String userName,
        String email,
        String password
) {
    public static UserApi toUserApi(User user) {
        return UserApi.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }
}

