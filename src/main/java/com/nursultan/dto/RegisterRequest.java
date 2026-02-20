package com.nursultan.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String userName;
    private String userSurname;
    private String password;
}