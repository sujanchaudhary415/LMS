package com.example.demo.service.impl;

import com.example.demo.dto.response.EnrollmentResponse;
import com.example.demo.entity.Course;
import com.example.demo.entity.Enrollment;
import com.example.demo.entity.User;
import com.example.demo.enums.AppRole;
import com.example.demo.exception.AccessDeniedException;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.EnrollmentMapper;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.util.SecurityUtil;
import com.example.demo.service.EnrollmentService;
import com.example.demo.util.PageableUtil;
import com.example.demo.util.PaginationUtil;
import com.example.demo.wrapper.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Override
    @Transactional
    public EnrollmentResponse enrollStudent(Long courseId) {
        Long studentId= SecurityUtil.getCurrentUserId();
        User student=userRepository.findById(studentId).orElseThrow(()->new ResourceNotFoundException("Student","studentId",studentId));

        Course course=courseRepository.findById(courseId).orElseThrow(()->new ResourceNotFoundException("Course","courseId",courseId));

        if(enrollmentRepository.existsByStudent_UserIdAndCourse_Id(studentId, courseId)){
            throw new DuplicateResourceException("Student is already enrolled in this course");
        }

        Enrollment enrollment=new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(Instant.now());

        Enrollment savedEnrollment=enrollmentRepository.save(enrollment);
        return enrollmentMapper.toResponse(savedEnrollment);
    }

    @Override
    public EnrollmentResponse getEnrollmentById(Long enrollmentId) {
         Enrollment enrollment =enrollmentRepository.findById(enrollmentId).orElseThrow(()->new ResourceNotFoundException("Enrollment","enrollmentId",enrollmentId));
         return enrollmentMapper.toResponse(enrollment);
    }

    @Override
    public PagedResponse<EnrollmentResponse> getStudentEnrollments(Long studentId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        User student=userRepository.findById(studentId).orElseThrow(()->new ResourceNotFoundException("Student","studentId",studentId));

        if(student.getRoles().contains(AppRole.STUDENT)){
            throw new AccessDeniedException("User is not a student");
        }

        Pageable pageDetails= PageableUtil.create(pageNumber,pageSize,sortBy,sortDir);
        Page<Enrollment>enrollmentPage= enrollmentRepository.findByStudent_UserId(studentId,pageDetails);
        List<EnrollmentResponse>content=enrollmentPage.getContent()
                .stream()
                .map(enrollmentMapper::toResponse)
                .toList();
        return PaginationUtil.build(enrollmentPage,content);
    }

    @Override
    public PagedResponse<EnrollmentResponse> getCourseEnrollments(Long courseId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Pageable pageDetails=PageableUtil.create(pageNumber,pageSize,sortBy,sortDir);
        Page<Enrollment>enrollmentPage=enrollmentRepository.findByCourse_Id(courseId,pageDetails);
        List<EnrollmentResponse>content =enrollmentPage.getContent()
                .stream()
                .map(enrollmentMapper::toResponse)
                .toList();
        return PaginationUtil.build(enrollmentPage,content);
    }

    @Override
    public void unenrollStudent(Long studentId, Long enrollmentId) {
        Enrollment enrollment=enrollmentRepository.findById(enrollmentId).orElseThrow(()->new ResourceNotFoundException("Enrollment","enrollmentId",enrollmentId));

        if(!enrollment.getStudent().getUserId().equals(studentId)){
            throw new AccessDeniedException("You are not allowed to do this");
        }
        enrollmentRepository.delete(enrollment);
    }
}
