package com.raksul.userApi.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;

@Data
public class UserInputDto {
    @Email
    private String email;

    @Length(min = 8)
    private String password;
}
