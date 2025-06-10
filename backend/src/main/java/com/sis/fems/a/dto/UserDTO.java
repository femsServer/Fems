package com.sis.fems.a.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String userId;
    private String userPw;
    private String userName;
    private String phone;
    private String email;
    private String roleLevel;
    private String useYn;
}
