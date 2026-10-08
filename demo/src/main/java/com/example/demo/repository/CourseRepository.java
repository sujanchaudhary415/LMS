package com.example.demo.repository;

import com.example.demo.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Page<Course> findByInstructor_UserId(Long userId, Pageable pageable);
    @EntityGraph(attributePaths = "lesson")
    Optional<Course> findById(Long courseId);

    @EntityGraph(attributePaths = "lesson")
    Page<Course> findAll(Pageable pageable);
}
