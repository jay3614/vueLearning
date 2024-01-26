package com.example.vuebackboard.controller;

import com.example.vuebackboard.dto.UserDTO;
import com.example.vuebackboard.entity.UserEntity;
import com.example.vuebackboard.service.UserService;
import com.example.vuebackboard.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    
    private final AuthenticationManager authenticationManager;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> paramMap) {
        String userId = paramMap.get("user_id");
        String userPw = paramMap.get("user_pw");

        UserDetails loginUser = userService.loadUserByUsername(userId); //userId로 정보 가져오기

        Authentication authentication = authenticationManager.authenticate(     //가져온 정보와 입력한 비밀번호로 검증
                new UsernamePasswordAuthenticationToken(loginUser, userPw)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);   // 검증 통과 후 authentication 세팅

        String accessToken = jwtUtil.createToken(loginUser.getUsername(), loginUser.getUsername());     //accessToken 생성

        Map<String, Object> result = new HashMap<>();
        result.put("user_id", loginUser.getUsername());
        result.put("user_token", accessToken);
        result.put("user_role", loginUser.getAuthorities().stream().findFirst().get().getAuthority());

        return ResponseEntity.ok(result);
    }
    
    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        try {
            userService.register(userDTO);
            return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("이미 존재하는 아이디 입니다.");
        }
    }
    
    // ID 찾기
    @PostMapping("/findId")
    public ResponseEntity<Map<String, Object>> findId(@RequestBody UserDTO userDTO) {
    	try {
    		String userId = userService.findId(userDTO);
    		
    		Map<String, Object> result = new HashMap<>();
            result.put("user_id", userId);
            
    		return ResponseEntity.ok().body(result);
    	} catch (RuntimeException e) {
    		e.getStackTrace();
    		e.getMessage();
    		return null;
    	}
    }
    
    // 비밀번호 찾기
    @PostMapping("/findPw")
    public ResponseEntity<?> findPw(@RequestBody UserDTO userDTO) throws Exception {
    	log.info("요청된 이메일 : " + userDTO.getUserEmail());
    	
    	if(!userService.checkEmail(userDTO.getUserEmail())) {
			return ResponseEntity.badRequest().body("해당 이메일을 사용하는 회원을 찾을 수 없습니다. ");
		}
    	
    	String tmpPassword = userService.getTmpPassword();	// 랜덤의 비밀번호 생성
		userService.updatePassword(tmpPassword, userDTO.getUserEmail());	// 바뀐 비밀번호 db에 업데이트
		userService.findPw(tmpPassword, userDTO.getUserEmail());	// 임시 비밀번호 메일로 전송
		
		return ResponseEntity.ok("가입하신 이메일로 임시 비밀번호를 전송하였습니다.");
    }
    
    // 내 정보 가져오기
    @PostMapping("/myInfo")
    public ResponseEntity<Map<String, Object>> myInfo(@RequestBody UserDTO userDTO) {
    	
    	UserEntity userInfo = userService.findById(userDTO.getUserId());
		
		String userId = userInfo.getUserId();
		String userName = userInfo.getUserName();
		String userEmail = userInfo.getUserEmail();
		
		Map<String, Object> result = new HashMap<>();
        result.put("user_id", userId);
        result.put("user_name", userName);
        result.put("user_email", userEmail);
		
		return ResponseEntity.ok().body(result);
    }
    
    // 내 정보 수정하기
    @PatchMapping("/updateInfo")
    public UserEntity update(@RequestBody UserDTO userDTO) {
    	return userService.update(userDTO);
    }
}