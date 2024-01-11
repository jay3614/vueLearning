package com.example.vuebackboard.service;

import com.example.vuebackboard.dto.UserDTO;
import com.example.vuebackboard.entity.UserEntity;
import com.example.vuebackboard.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (userEntity.getUserId().equals(username)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new User(userEntity.getUserId(), userEntity.getUserPw(), authorities);
    }
    
    public UserEntity register(UserDTO userDTO) {
    	
    	UserEntity id = UserEntity.builder().userId(userDTO.getUserId()).build();
    	String userId = id.getUserId();
    	
		if(userRepository.existsByUserId(userId)) {
			log.warn("이미 존재하는 아이디 입니다.");
			throw new RuntimeException("이미 존재하는 아이디 입니다.");
		}
    	
    	String encPassword = passwordEncoder.encode(userDTO.getUserPw());
    	
    	UserEntity entity = UserEntity.builder()
                .userId(userDTO.getUserId())
                .userEmail(userDTO.getUserEmail())
                .userName(userDTO.getUserName())
                .userPw(encPassword)
                .build();
        return userRepository.save(entity);
    }
    
    // ID 찾기
    public String findId(UserDTO userDTO) {
    	String userId = userRepository.findIdByEmailAndName(userDTO.getUserEmail(), userDTO.getUserName());
    	
    	return userId;
    }
    
    // pw 찾기
    public void findPw(UserDTO userDTO) {
    	
    }
}