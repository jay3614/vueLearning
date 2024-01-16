package com.example.vuebackboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vuebackboard.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUserId(String userId);
	
	// 유저 존재여부 확인
	Boolean existsByUserId(String userId);
	
	// 이메일 존재여부 확인
	Boolean existsByUserEmail(String userEmail);
	
	// 이메일로 유저 객체 리턴
	UserEntity findByUserEmail(String userEmail);
	
	// 이메일과 이름에 해당하는 유저의 아이디 리턴
	@Query(value = "SELECT user_id from tb_user where user_email=:user_email and user_name=:user_name", nativeQuery = true)
	String findIdByEmailAndName(@Param("user_email") String userEmail, @Param("user_name") String userName);
}