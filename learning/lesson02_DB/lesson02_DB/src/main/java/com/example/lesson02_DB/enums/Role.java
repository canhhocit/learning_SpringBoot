package com.example.lesson02_DB.enums;

// enum (enumeration) dùng để định nghĩa tập các hằng số cố định,
// // giúp code rõ nghĩa – an toàn – dễ bảo trì hơn so với static final
public enum Role {
  ADMIN,
  USER,
  STAFF
}
//  1 user -> many role
// 1 role -> many Permission
