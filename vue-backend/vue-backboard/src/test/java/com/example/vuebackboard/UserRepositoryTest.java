package com.example.vuebackboard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.vuebackboard.repository.UserRepository;
import com.example.vuebackboard.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    @Test
//    void test_1(){
//        String encPassword = passwordEncoder.encode("1234");
//        UserEntity userEntity = UserEntity.builder()
//                .userId("jay3614")
//                .userPw(encPassword)
//                .userName("테스트유저2")
//                .build();
//
//        UserEntity savedUser = userRepository.save(userEntity);
//        assertThat(userEntity.getUserId()).isEqualTo(savedUser.getUserId());
//    }
    
    @Test    
    void test_2(){
//        String encPassword = passwordEncoder.encode("test_password");
//
//        UserEntity user = userRepository.findByUserId("test_user").orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
//
//        assertThat(user.getUserPw()).isEqualTo(encPassword);
    	
    	String userId = "test_user";
        String userPw = "test_password";
        UserDetails user = userService.loadUserByUsername(userId);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, userPw);
        authenticationManager.authenticate(authenticationToken);

        assertThat(authenticationToken.getCredentials()).isEqualTo(userPw);

        System.out.println("getCredentials: " + authenticationToken.getCredentials());
        System.out.println("userPw: " + userPw);
    }

}