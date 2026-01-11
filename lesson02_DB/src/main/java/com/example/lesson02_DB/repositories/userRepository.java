package com.example.lesson02_DB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.lesson02_DB.entity.user;
@Repository
public interface userRepository extends JpaRepository<user, String>{
     boolean existsByUsername(String username) ;
    
} 
