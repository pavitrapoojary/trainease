package com.trainease.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CourseWiseStatusCount {
    private Integer toBeStartedCount;
    private Integer inProgressCount;
    private Integer completedCount;
}
