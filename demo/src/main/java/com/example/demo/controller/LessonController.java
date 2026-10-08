package com.example.demo.controller;
import com.example.demo.constant.AppConstant;
import com.example.demo.dto.request.LessonRequest;
import com.example.demo.dto.request.LessonUpdateRequest;
import com.example.demo.dto.response.LessonResponse;
import com.example.demo.service.LessonService;
import com.example.demo.wrapper.ApiResponse;
import com.example.demo.wrapper.PagedResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@Validated
@RequiredArgsConstructor
@Tag(name="Lesson",description = "Lesson management APIs")
public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<ApiResponse<LessonResponse>>createLesson(@PathVariable Long courseId, @RequestBody @Valid LessonRequest request){
        LessonResponse response =lessonService.createLesson(courseId,request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Lesson created successfully",response));
    }

    @GetMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<ApiResponse<LessonResponse>> getLessonById(@PathVariable @Positive Long courseId,@PathVariable @Positive Long lessonId){
        LessonResponse response =lessonService.getLessonById(courseId,lessonId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Lesson fetched successfully",response));
    }

    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<ApiResponse<PagedResponse<LessonResponse>>> getLessonsByCourse(@PathVariable @Positive Long courseId, @RequestParam(name = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER)int pageNumber, @RequestParam(name = "pageSize",defaultValue = AppConstant.PAGE_SIZE)int pageSize, @RequestParam(name = "sortBy",defaultValue = AppConstant.SORT_BY_LESSON)String sortBy, @RequestParam(name = "sortDir",defaultValue = AppConstant.SORT_DIR)String sortDir){
        PagedResponse<LessonResponse>response=lessonService.getLessonsByCourse(courseId, pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("All lesson fetched successfully of this course",response));

    }

    @DeleteMapping("/instructors/{instructorId}/lessons/{lessonId}")
    public ResponseEntity<ApiResponse<Void>> deleteLesson(@PathVariable Long instructorId, @PathVariable Long lessonId){
        lessonService.deleteLessonById(lessonId, instructorId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Lesson deleted successfully"));
    }

    @PatchMapping("/instructors/{instructorId}/lessons/{lessonId}")
    public ResponseEntity<ApiResponse<LessonResponse>> updateLessonById(@PathVariable Long lessonId, @PathVariable Long instructorId, @RequestBody @Valid LessonUpdateRequest request){
       LessonResponse response=lessonService.UpdateLessonById(lessonId,instructorId,request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Lesson updated successfully"));
    }

}
