package com.example.demo.mapper;

import com.example.demo.dto.response.EnrollmentResponse;
import com.example.demo.entity.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(source = "student.userId", target = "studentId")
    @Mapping(source = "student.firstName", target = "studentName")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.description", target = "courseTitle")
    @Mapping(source = "course.instructor.firstName", target = "instructorName")
    EnrollmentResponse toResponse(Enrollment enrollment);
}
