package com.canhhocit.learn00.workwDB;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>{
/*
JpaRepository đã có sẵn:

save

findAll

findById

deleteById
*/
} 
