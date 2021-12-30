package com.example.demo_woojin.controller;


import com.example.demo_woojin.dto.ResponseDTO;
import com.example.demo_woojin.dto.UserDTO;
import com.example.demo_woojin.model.UserEntity;
import com.example.demo_woojin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController     //JSON 형태로 객체를 반환  @Controller + @ResponseBody
@RequestMapping("/auth")
public class UserController {
    //Service 를 이용하여
    // 1. 현재 사용자를 가져오는 기능
    // 2. 레지스터 기능을 구현

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        try{
            //요청을 이용해서 저장할 사용자 만들기
            UserEntity user = UserEntity.builder().email(userDTO.getEmail()).username(userDTO.getUsername())
                    .password(userDTO.getPassword()).build();

            //Service를 이용하여 Repository에 사용자 저장
            UserEntity registeredUser = userService.create(user);

            UserDTO responseUserDTO = UserDTO.builder().email(registeredUser.getEmail())
                    .id(registeredUser.getId()).username(registeredUser.getUsername()).build();

            return ResponseEntity.ok().body(responseUserDTO);

        }catch (Exception e){
            //사용자 정보는 항상 하나이므로 리스트로 만들어야 하는 ResponseDTO를 사용하지 않고 그냥 UserDTO로 리턴하자.

            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
        UserEntity user = userService.getByCredentials(userDTO.getEmail(), userDTO.getPassword());

        if(user != null){
            final UserDTO responseUserDTO = UserDTO.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .build();
            //ResponseEntity  HttpHeader 와  HttpBody를 포함하는 클래스
            return ResponseEntity.ok().body(responseUserDTO);
        }else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login Failed !!!!")
                    .build();
            return ResponseEntity.badRequest()
                    .body(responseDTO);
        }
    }

}
