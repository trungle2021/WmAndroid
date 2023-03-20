package com.example.wmandroid.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private int id;
    private String first_name;
    private String last_name;
    private String address;
    private String phone;
    private String email;
    private String gender;
    private String avatar;
    private String avatarFromDB;
    public String getAvatar() {
        return this.avatar != null ? this.avatar : this.avatarFromDB;
    }

}
