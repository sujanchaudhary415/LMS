package com.example.demo.repository;

import com.example.demo.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson,Long> {
    Optional<Lesson>findByIdAndCourse_id(Long courseId, Long lessonId);
    Page<Lesson> findByCourse_id(Long courseId, Pageable pageable);
}
