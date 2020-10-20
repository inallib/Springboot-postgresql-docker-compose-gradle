package com.raksul.userApi.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class UserResponseDto {
    private Integer id;
    private String email;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
