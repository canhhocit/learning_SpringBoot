package com.example.lesson02_DB.services;// NOSONAR

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.lesson02_DB.dto.request.PermissionRequest;
import com.example.lesson02_DB.dto.response.PermissionResponse;
import com.example.lesson02_DB.entity.Permission;
import com.example.lesson02_DB.mapper.PermissionMapper;
import com.example.lesson02_DB.repositories.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService {

  PermissionRepository permissionRepository;
  PermissionMapper permissionMapper;

  public PermissionResponse create(PermissionRequest request) {
    Permission permission = permissionMapper.toPermission(request);
    permission = permissionRepository.save(permission);
    return permissionMapper.toPermissionResponse(permission);
  }

  public List<PermissionResponse> getAll() {
    var permissions = permissionRepository.findAll();
    return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
  }

  public void delete(String permission) {
    permissionRepository.deleteById(permission);
  }
}
