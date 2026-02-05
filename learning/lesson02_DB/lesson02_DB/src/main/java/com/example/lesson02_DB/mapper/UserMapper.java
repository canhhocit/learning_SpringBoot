package com.example.lesson02_DB.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.lesson02_DB.dto.request.UserCreationRequest;
import com.example.lesson02_DB.dto.request.UserUpdateRequest;
import com.example.lesson02_DB.dto.response.UserResponse;
import com.example.lesson02_DB.entity.User;

// @Mapper(componentModel = "spring")
@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "roles", ignore = true)
  User toUser(UserCreationRequest request);

  // @Mapping (source = "firstname", target = "lastname")
  // @Mapping(target = "roles", ignore = true)
  UserResponse toUserResponse(User u);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "username", ignore = true)
  @Mapping(target = "roles", ignore = true)
  void updateUser(@MappingTarget User u, UserUpdateRequest request);
}
