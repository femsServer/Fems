package com.sis.fems.a.mapper;

import com.sis.fems.a.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

 

    List<UserDTO> getUserList();    // 사용자 목록 조회
    void insertUser(UserDTO user);  // 사용자 등록
    int deleteUser(UserDTO user);   // 사용자 삭제
}