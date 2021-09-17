package com.moshhsa.model;

import lombok.Data;

@Data
public class PasswordRequest {
    private String token;
    private String oldPassword;
    private String newPassword;
}
