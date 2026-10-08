package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnrollmentRequest {
    @NotNull
    private Long studentId;
    @NotNull
    private Long courseId;
}
