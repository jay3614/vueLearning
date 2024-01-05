package com.example.vuebackboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vuebackboard.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUserId(String userId);
	
	Boolean existsByUserId(String userId);
}