package com.example.lesson02_DB.controllers;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.lesson02_DB.dto.request.ApiResponse;
import com.example.lesson02_DB.dto.request.UserCreationRequest;
import com.example.lesson02_DB.dto.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class UserControllerIntegrationTest {

  @Container
  // static final MySQLContainer<?> MY_SQL_CONTAINER = new
  // MySQLContainer<>("mysql:8.0");
  @SuppressWarnings("resource")
  static final MySQLContainer<?> MY_SQL_CONTAINER =
      new MySQLContainer<>("mysql:8.0")
          .withDatabaseName("testdb")
          .withUsername("test")
          .withPassword("test");

  @DynamicPropertySource
  static void configDataSource(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
    registry.add("spring.datasource.driverClassName", () -> "com.mysql.cj.jdbc.Driver");
    registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
  }

  @Autowired private MockMvc mockMvc;

  private UserCreationRequest request;
  private UserResponse userResponse;
  private ApiResponse<UserResponse> apiResponse;
  private LocalDate dob;

  @BeforeEach
  void initData() {
    dob = LocalDate.of(2005, 1, 1);

    request =
        UserCreationRequest.builder()
            .username("canhhuu")
            .firstname("Canh")
            .lastname("Pham Huu")
            .password("123456")
            .dob(dob)
            .build();

    userResponse =
        UserResponse.builder()
            .id("heheheheheh")
            .username("canhhuu")
            .firstname("Canh")
            .lastname("Pham Huu")
            .dob(dob)
            .build();

    apiResponse = ApiResponse.<UserResponse>builder().code(1000).result(userResponse).build();
  }

  // Helper method để chuyển object thành JSON
  private String toJson(Object obj) throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper.writeValueAsString(obj);
  }

  @Test
  void createUser_validRequest_success() throws Exception {

    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/users")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
            .andExpect(MockMvcResultMatchers.jsonPath("result.username").value("canhhuu"))
            .andExpect(MockMvcResultMatchers.jsonPath("result.firstname").value("Canh"))
            .andExpect(MockMvcResultMatchers.jsonPath("result.lastname").value("Pham Huu"));
    log.info("Result: {}", response.andReturn().getResponse().getContentAsString());
  }

  // @Test
  // void createUser_usernameInvalid_fail() throws Exception {
  // // GIVEN
  // request.setUsername("us"); // username < 3 ký tự

  // // WHEN & THEN
  // mockMvc.perform(MockMvcRequestBuilders
  // .post("/users")
  // .contentType(MediaType.APPLICATION_JSON_VALUE)
  // .content(toJson(request)))
  // .andExpect(MockMvcResultMatchers.status().isBadRequest())
  // .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
  // .andExpect(MockMvcResultMatchers.jsonPath("message")
  // .value("Username must be at least 3 characters"));
  // }

  // @Test
  // void createUser_passwordInvalid_fail() throws Exception {
  // // GIVEN
  // request.setPassword("123"); // password < 5 ký tự

  // // WHEN & THEN
  // mockMvc.perform(MockMvcRequestBuilders
  // .post("/users")
  // .contentType(MediaType.APPLICATION_JSON_VALUE)
  // .content(toJson(request)))
  // .andExpect(MockMvcResultMatchers.status().isBadRequest())
  // .andExpect(MockMvcResultMatchers.jsonPath("code").value(1005))
  // .andExpect(MockMvcResultMatchers.jsonPath("message")
  // .value("Password must be at least 5 characters"));
  // }

  // @Test
  // void createUser_dobInvalid_fail() throws Exception {
  // // GIVEN
  // request.setDob(LocalDate.of(2015, 1, 1)); // Chưa đủ 16 tuổi

  // // WHEN & THEN
  // mockMvc.perform(MockMvcRequestBuilders
  // .post("/users")
  // .contentType(MediaType.APPLICATION_JSON_VALUE)
  // .content(toJson(request)))
  // .andExpect(MockMvcResultMatchers.status().isBadRequest())
  // .andExpect(MockMvcResultMatchers.jsonPath("code").value(1008))
  // .andExpect(MockMvcResultMatchers.jsonPath("message")
  // .value("Your age must be at least 16"));
  // }
}
