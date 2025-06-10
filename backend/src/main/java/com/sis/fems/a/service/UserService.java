package com.sis.fems.a.service;

import com.sis.fems.a.dto.UserDTO;
import com.sis.fems.common.util.jspUtil;
import com.sis.fems.a.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserMapper userMapper;
    private final jspUtil jspUtil;


    // 사용자 목록 조회
    public List<UserDTO> getUserList() {
        return userMapper.getUserList();
    }
    // 사용자 등록
    public void registerUser(UserDTO user) {
        // PW 암호화
        String encrypted = jspUtil.sha256Encrypt(user.getUserPw());
        user.setUserPw(encrypted.substring(0, 48));
        userMapper.insertUser(user);
    }

    // 사용자 삭제
    public void deleteUser(UserDTO user) {
        userMapper.deleteUser(user);
    }

}