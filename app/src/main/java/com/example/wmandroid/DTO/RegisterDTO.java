package com.example.wmandroid.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private int id;

    private String name;

    private String address;

    private String phone;

    private String joinDate;

    private Double salary;

    private String email;

    private Integer isLeader;

    private Integer team_id;

    private String gender;

    private String avatar;

    //emp_account
    private String username;

    private String password;

    private String role;

    private Integer employeeId;

}
