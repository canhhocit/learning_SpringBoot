package com.example.lesson02_DB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.lesson02_DB.entity.InvalidateToken;

@Repository
public interface InvalidateTokenRepository extends JpaRepository<InvalidateToken, String> {}
