package com.example.demo.mapper;

import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.InstructorResponse;
import com.example.demo.dto.response.StudentResponse;
import com.example.demo.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        uses = CourseMapper.class)
public interface UserMapper {

    StudentResponse toStudentResponse(User user);
    InstructorResponse toInstructorResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromRequest(UserUpdateRequest request, @MappingTarget User student);
}
