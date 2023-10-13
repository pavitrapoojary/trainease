package com.trainease.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchProgress {
    private String emailId;
    private List<CourseProgressStatus> courseProgressStatusList;
}
