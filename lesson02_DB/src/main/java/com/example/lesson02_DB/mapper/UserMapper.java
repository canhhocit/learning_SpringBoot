package com.example.lesson02_DB.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.lesson02_DB.dto.request.userCreationRequest;
import com.example.lesson02_DB.dto.request.userUpdateRequest;
import com.example.lesson02_DB.dto.response.UserResponse;
import com.example.lesson02_DB.entity.user;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    user toUser(userCreationRequest request);
    
    // @Mapping (source = "firstname", target = "lastname")
    UserResponse toUserResponse(user u);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    void updatUser(@MappingTarget user u, userUpdateRequest request);

}