package com.example.wmandroid.DTO;

import lombok.Data;

@Data
public class PasswordDTO {
    private String newPassword;
    private String confirmPassword;
    private String token;
}
