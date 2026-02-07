package com.example.lesson02_DB.entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;
  @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
  // k phan biet hoa thuong
  String username;
  String password;
  String firstname;
  String lastname;
  LocalDate dob;
  // mọi phần tử trong set là unique
  // list cho phép nhiều ptu tồn tại (uer,user) còn set chỉ cho phép có 1 item
  // user tồn tại
  @ManyToMany Set<Role> roles;
}
