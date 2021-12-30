package com.example.demo_woojin.service;

import com.example.demo_woojin.model.UserEntity;
import com.example.demo_woojin.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity){
        //final은 객체를 한번 생성하면 재생성 할 수 없음. 대신 필드값은 변경가능
        if(userEntity==null || userEntity.getEmail()==null){
            throw new RuntimeException("Invalid arguments");
        }

        final String email = userEntity.getEmail();

        if(userRepository.existsByEmail(email)){
            log.warn("Email already exist {}",email);
            throw new RuntimeException("email already exists");
        }

        return userRepository.save(userEntity);

    }
    public UserEntity getByCredentials(final String email, final String password){
        //사용자 인증에 필요한 정보를 반환 보통 id , password
        return userRepository.findByEmailAndPassword(email,password);

    }
}
