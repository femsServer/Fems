package com.sis.fems.a.controller;

import com.sis.fems.a.dto.UserDTO;
import com.sis.fems.a.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 사용자 목록 조회
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUserList() {
        List<UserDTO> users = userService.getUserList();
        return ResponseEntity.ok(users);
    }
    
    // 사용자 등록
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserDTO user) {
        userService.registerUser(user);
        return ResponseEntity.ok("사용자 등록 완료");
    }

    //사용자 수정

    // 사용자 삭제
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody UserDTO user) {
        userService.deleteUser(user);
        return ResponseEntity.ok("사용자 삭제 완료");
    }


}
