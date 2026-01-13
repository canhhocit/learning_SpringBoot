package com.example.lesson02_DB.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.lesson02_DB.dto.request.ApiResponse;
import com.example.lesson02_DB.dto.request.UserCreationRequest;
import com.example.lesson02_DB.dto.request.UserUpdateRequest;
import com.example.lesson02_DB.dto.response.UserResponse;
import com.example.lesson02_DB.entity.User;
import com.example.lesson02_DB.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/users")
public class UserControler {
    @Autowired
    private UserService uService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse <User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(uService.createUser(request));
        return apiResponse;
    }
    @GetMapping
    List<User> getUsers(){
       return uService.getUsers();
    }
    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable("userId") String userId){
        return uService.getUser(userId);
    }
    //update
    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable String userId,@RequestBody UserUpdateRequest request ){
        return uService.updatUser(userId, request);
    } 
    //delete
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId){
        uService.deleteUser(userId);
        return "deleted !!!";
    }
    
}
