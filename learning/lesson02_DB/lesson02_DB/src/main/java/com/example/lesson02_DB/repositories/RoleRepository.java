package com.example.lesson02_DB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.lesson02_DB.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
