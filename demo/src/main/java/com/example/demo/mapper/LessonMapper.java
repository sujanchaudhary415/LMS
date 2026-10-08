package com.example.demo.mapper;

import com.example.demo.dto.request.LessonRequest;
import com.example.demo.dto.request.LessonUpdateRequest;
import com.example.demo.dto.response.LessonResponse;
import com.example.demo.entity.Lesson;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    Lesson toEntity(LessonRequest request);

    @Mapping(source = "course.id",target = "courseId")
    LessonResponse toLessonResponse(Lesson lesson);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLessonFromRequest(LessonUpdateRequest request, @MappingTarget Lesson lesson);
}
