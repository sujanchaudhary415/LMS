package com.example.demo.service.impl;

import com.example.demo.dto.request.LessonRequest;
import com.example.demo.dto.request.LessonUpdateRequest;
import com.example.demo.dto.response.LessonResponse;
import com.example.demo.entity.Course;
import com.example.demo.entity.Lesson;
import com.example.demo.exception.AccessDeniedException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.LessonMapper;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.LessonRepository;
import com.example.demo.service.LessonService;
import com.example.demo.util.PageableUtil;
import com.example.demo.util.PaginationUtil;
import com.example.demo.wrapper.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final CourseRepository courseRepository;
    private final LessonMapper lessonMapper;
    private final LessonRepository lessonRepository;

    @Override
    public LessonResponse createLesson(Long courseId, LessonRequest request) {
        Course course=courseRepository.findById(courseId).orElseThrow(()->new ResourceNotFoundException("Course","courseId",courseId));

        Lesson lesson =lessonMapper.toEntity(request);
        lesson.setCourse(course);
        course.getLesson().add(lesson);
        Lesson savedLesson=lessonRepository.save(lesson);
        return lessonMapper.toLessonResponse(savedLesson);
    }

    @Override
    public LessonResponse getLessonById(Long courseId,Long lessonId) {
          Lesson lesson=lessonRepository.findByIdAndCourse_id(courseId,lessonId).orElseThrow(()->new ResourceNotFoundException("Lesson","lessonId",lessonId));
          return lessonMapper.toLessonResponse(lesson);
    }

    @Override
    public PagedResponse<LessonResponse> getLessonsByCourse(Long courseId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Pageable pageDetails= PageableUtil.create(pageNumber,pageSize,sortBy,sortDir);
        Page<Lesson> lessonPage=lessonRepository.findByCourse_id(courseId,pageDetails);
        List<LessonResponse> content=lessonPage.getContent()
                .stream()
                .map(lessonMapper::toLessonResponse)
                .toList();
        return PaginationUtil.build(lessonPage,content);
    }

    @Override
    public void deleteLessonById(Long lessonId,Long instructorId) {
        Lesson lesson=lessonRepository.findById(lessonId).orElseThrow(()->new ResourceNotFoundException("Lesson","lessonId",lessonId));
        Long courseInstructorId=lesson.getCourse().getInstructor().getUserId();
        if(!courseInstructorId.equals(instructorId)){
            throw new AccessDeniedException("You are not allowed to delete this lesson");
        }
        lessonRepository.delete(lesson);
    }

    @Override
    public LessonResponse UpdateLessonById(Long lessonId, Long instructorId, LessonUpdateRequest request) {
        Lesson lesson=lessonRepository.findById(lessonId).orElseThrow(()->new ResourceNotFoundException("Lesson","lessonId",lessonId));
        Long courseInstructorId=lesson.getCourse().getInstructor().getUserId();
        if(!courseInstructorId.equals(instructorId)){
            throw new AccessDeniedException("You cannot update this lesson");
        }
        lessonMapper.updateLessonFromRequest(request,lesson);
        Lesson updatedLesson=lessonRepository.save(lesson);
        return lessonMapper.toLessonResponse(updatedLesson);
    }

}
