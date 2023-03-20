package com.example.wmandroid.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccountDTO {
    private int id;
    private String username;
    private String password;
    private Integer customerId;
}
