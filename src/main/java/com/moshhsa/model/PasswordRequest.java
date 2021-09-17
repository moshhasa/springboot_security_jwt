package com.moshhsa.model;

import lombok.Data;

@Data
public class PasswordRequest {
    private Long userId;
    private String oldPassword;
    private String newPassword;
}
