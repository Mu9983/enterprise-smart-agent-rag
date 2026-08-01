package com.mu9983.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyUserPassword {
    private Integer id;
    private String username;
    private String password;
}
