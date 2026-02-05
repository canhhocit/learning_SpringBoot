package com.example.lesson02_DB.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.lesson02_DB.dto.request.RoleRequest;
import com.example.lesson02_DB.dto.response.PermissionResponse;
import com.example.lesson02_DB.dto.response.RoleResponse;
import com.example.lesson02_DB.entity.Permission;
import com.example.lesson02_DB.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

  @Mapping(target = "permission", ignore = true)
  Role toRole(RoleRequest request);

  @Mapping(target = "permissions", source = "permission", qualifiedByName = "toPermissionResponses")
  RoleResponse toRoleResponse(Role role);

  @Named("toPermissionResponses")
  default Set<PermissionResponse> toPermissionResponses(Set<Permission> permissions) {
    return permissions.stream()
        .map(
            permission ->
                PermissionResponse.builder()
                    .name(permission.getName())
                    .description(permission.getDescription())
                    .build())
        .collect(Collectors.toSet());
  }
}
