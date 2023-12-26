package com.example.vuebackboard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.vuebackboard.util.JwtUtil;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    JwtUtil jwtUtil;

    @Test
    void test_1(){
        String userId = "user1";
        String userName = "사용자1";

        String token = jwtUtil.createToken(userId, userName);

        System.out.println("Token : " + token);

        assertThat(jwtUtil.decodeToken(token).getClaim("userName").asString()).isEqualTo(userName);
    }
}