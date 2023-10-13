package com.trainease.dto;

import com.trainease.entity.Batch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginData {
    private String emailId;
    private UserRole role;
    private Batch batch;

}
