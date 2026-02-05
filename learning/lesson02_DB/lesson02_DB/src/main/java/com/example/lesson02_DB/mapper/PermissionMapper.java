package com.example.lesson02_DB.mapper;

import org.mapstruct.Mapper;

import com.example.lesson02_DB.dto.request.PermissionRequest;
import com.example.lesson02_DB.dto.response.PermissionResponse;
import com.example.lesson02_DB.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

  Permission toPermission(PermissionRequest request);

  PermissionResponse toPermissionResponse(Permission permission);
}
