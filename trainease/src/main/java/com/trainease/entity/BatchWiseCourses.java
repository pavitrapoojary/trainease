package com.trainease.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchWiseCourses {
    @Id
    private String batchId;
    private List<Course> courses;
}
