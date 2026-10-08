package com.example.demo.service.impl;

import com.example.demo.dto.request.CourseRequest;
import com.example.demo.dto.request.CourseUpdateRequest;
import com.example.demo.dto.response.CourseResponse;
import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.enums.AppRole;
import com.example.demo.exception.AccessDeniedException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.services.impl.UserDetailsImpl;
import com.example.demo.security.util.SecurityUtil;
import com.example.demo.service.CourseService;
import com.example.demo.util.PageableUtil;
import com.example.demo.util.PaginationUtil;
import com.example.demo.wrapper.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final UserRepository userRepository;
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;

    @Override
    public CourseResponse createCourse(CourseRequest request) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails= (UserDetailsImpl)authentication.getPrincipal();
        Long instructorId=userDetails.getUserId();
        User instructor=userRepository.findById(instructorId).orElseThrow(()->new ResourceNotFoundException("Instructor","userId",instructorId));
        Course course=courseMapper.toEntity(request);
        course.setInstructor(instructor);
        Course savedCourse=courseRepository.save(course);
        return courseMapper.toCourseResponse(savedCourse);
    }

    @Override
    public CourseResponse getCourseById(Long courseId) {
        Course course=courseRepository.findById(courseId).orElseThrow(()->new ResourceNotFoundException("Course","courseId",courseId));
        return courseMapper.toCourseResponse(course);
    }

    @Override
    public PagedResponse<CourseResponse> getCoursesByInstructors(Long instructorId, int pageNumber, int pageSize, String sortDir, String sortBy) {
        Pageable pageDetails= PageableUtil.create(pageNumber,pageSize,sortDir,sortBy);
        Page<Course> coursePage =courseRepository.findByInstructor_UserId(instructorId,pageDetails);
        List<CourseResponse>content=coursePage.getContent()
                .stream()
                .map(courseMapper::toCourseResponse)
                .toList();
        return PaginationUtil.build(coursePage,content);
    }

    @Override
    public PagedResponse<CourseResponse> getAllCourses(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Pageable pageDetails=PageableUtil.create(pageNumber,pageSize,sortBy,sortDir);
        Page<Course>coursePage=courseRepository.findAll(pageDetails);
        List<CourseResponse>content=coursePage.getContent()
                .stream()
                .map(courseMapper::toCourseResponse)
                .toList();
        return PaginationUtil.build(coursePage,content);
    }

    @Override
    public CourseResponse updateCourseById(Long courseId, Long instructorId, CourseUpdateRequest request) {
        Course course=courseRepository.findById(courseId).orElseThrow(()->new ResourceNotFoundException("Course","courseId",courseId));

        if(!course.getInstructor().getUserId().equals(instructorId)){
            throw new AccessDeniedException(
              "You are not allowed to update this course"
            );
        }

        courseMapper.updateCourseFromRequest(request,course);
        return courseMapper.toCourseResponse(course);
    }

    @Override
    public void deleteCourseById(Long courseId,Long instructorId) {
        Course course=courseRepository.findById(courseId).orElseThrow(()-> new ResourceNotFoundException("Course","courseId",courseId));

        if(!course.getInstructor().getUserId().equals(instructorId)){
            throw new AccessDeniedException("You are not allowed to delete this course");
        }

        courseRepository.deleteById(courseId);
    }


}
