package com.example.wmandroid.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @Setter @Getter
    private String username;

    @Setter @Getter
    private String password;
}
