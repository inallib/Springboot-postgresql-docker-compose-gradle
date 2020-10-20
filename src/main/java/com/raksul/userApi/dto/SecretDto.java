package com.raksul.userApi.dto;

import lombok.Data;

@Data
public class SecretDto {
    private Integer user_id;
    private final String secret = "All your base are belong to us";
}
