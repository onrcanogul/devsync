package com.devsync.authservice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDto {
    private String username;
    private String password;
}
