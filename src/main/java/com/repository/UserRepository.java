package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import com.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Transactional 
    @Modifying 
    @Query("UPDATE UserEntity u SET u.lastLogin = :lastLogin WHERE u.email = :email") 
    int validateLoginByEmail(@Param("lastLogin") LocalDateTime lastLogin, @Param("email") String email); 
    
	Optional<UserEntity> findByEmail(String email);   
    
    Boolean existsByEmail(String email);

}