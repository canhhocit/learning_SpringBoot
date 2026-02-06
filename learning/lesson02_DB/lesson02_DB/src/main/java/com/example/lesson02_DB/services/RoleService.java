package com.example.lesson02_DB.services; // NOSONAR

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.lesson02_DB.dto.request.RoleRequest;
import com.example.lesson02_DB.dto.response.RoleResponse;
import com.example.lesson02_DB.mapper.RoleMapper;
import com.example.lesson02_DB.repositories.PermissionRepository;
import com.example.lesson02_DB.repositories.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {

  RoleRepository roleRepository;
  PermissionRepository permissionRepository;

  RoleMapper roleMapper;

  public RoleResponse create(RoleRequest request) {
    var role = roleMapper.toRole(request);
    var permissions = permissionRepository.findAllById(request.getPermissions());
    role.setPermission(new HashSet<>(permissions));

    role = roleRepository.save(role);
    return roleMapper.toRoleResponse(role);
  }

  public List<RoleResponse> getAll() {
    return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
  }

  public void delete(String role) {
    roleRepository.deleteById(role);
  }
}
