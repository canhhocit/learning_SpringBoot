package com.example.lesson02_DB.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.lesson02_DB.dto.request.userCreationRequest;
import com.example.lesson02_DB.dto.request.userUpdateRequest;
import com.example.lesson02_DB.dto.response.UserResponse;
import com.example.lesson02_DB.entity.user;
import com.example.lesson02_DB.exception.AppException;
import com.example.lesson02_DB.exception.ErrorCode;
import com.example.lesson02_DB.mapper.UserMapper;
import com.example.lesson02_DB.repositories.userRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class userService {
    userRepository userRepo;

    UserMapper userMapper;
    public user createUser(userCreationRequest request) {
        
        if(userRepo.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        // tac dung cua lombok @builder
        // userCreationRequest request2 = userCreationRequest.builder()
        //                                 .username("")
        //                                 .firstname(" ")
        //                                 .build();
        //trc khi sd mapstruct
        // user user = new user();
        // user.setUsername(request.getUsername());
        // user.setPassword(request.getPassword());
        // user.setFirstname(request.getFirstname());
        // user.setLastname(request.getLastname());
        // user.setDob(request.getDob());
        // sd mapstruct
        user user = userMapper.toUser(request);

        return userRepo.save(user);

    }

    public List<user> getUsers() {
        return userRepo.findAll();
    }

    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userRepo.findById(id).orElseThrow(() -> new RuntimeException("User k ton tai")));
    }

    public UserResponse updatUser(String id, userUpdateRequest request) {
        user u = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User k ton tai"));
        userMapper.updatUser(u, request);
        // u.setPassword(request.getPassword());
        // u.setFirstname(request.getFirstname());
        // u.setLastname(request.getLastname());
        // u.setDob(request.getDob());
        return userMapper.toUserResponse(userRepo.save(u));
    }
    public void deleteUser(String id){
         userRepo.deleteById(id);
    }
}
