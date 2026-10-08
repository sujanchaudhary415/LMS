package com.example.demo.mapper;

import com.example.demo.dto.request.CourseRequest;
import com.example.demo.dto.request.CourseUpdateRequest;
import com.example.demo.dto.response.CourseResponse;
import com.example.demo.entity.Course;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = LessonMapper.class)
public interface CourseMapper {
    Course toEntity (CourseRequest request);

    @Mapping(source = "instructor.userId",target="instructorId")
    @Mapping(source = "lesson", target = "lessons")
    CourseResponse toCourseResponse (Course course);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCourseFromRequest(CourseUpdateRequest request, @MappingTarget Course course);
}
