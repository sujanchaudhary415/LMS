package com.example.demo.repository;

import com.example.demo.entity.Enrollment;
import com.example.demo.wrapper.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    boolean existsByStudent_UserIdAndCourse_Id(Long studentId,Long courseId);
    Page<Enrollment> findByStudent_UserId(Long studentId, Pageable pageable);
    Page<Enrollment> findByCourse_Id(Long courseId,Pageable pageable);
}
