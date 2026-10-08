package com.example.demo.security.mapper;

import com.example.demo.entity.User;
import com.example.demo.security.request.SignupRequest;
import com.example.demo.security.response.AuthResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "roles",ignore = true)
    @Mapping(target="userId",ignore = true)
    @Mapping(target="password",ignore=true)
    @Mapping(target="courses",ignore = true)
    @Mapping(target="enrollments",ignore = true)
    User toEntity(SignupRequest request);

    AuthResponse toResponse(User user);
}
