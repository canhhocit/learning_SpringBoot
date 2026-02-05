package com.example.lesson02_DB.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.lesson02_DB.dto.request.ApiResponse;
import com.example.lesson02_DB.dto.request.UserCreationRequest;
import com.example.lesson02_DB.dto.response.UserResponse;
import com.example.lesson02_DB.entity.User;
import com.example.lesson02_DB.exception.AppException;
import com.example.lesson02_DB.exception.ErrorCode;
import com.example.lesson02_DB.repositories.UserRepository;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {

  @Autowired private UserService userService;

  @MockitoBean private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  private UserCreationRequest request;
  private UserResponse userResponse;
  private User user;
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

    user =
        User.builder()
            .id("heheheheheh")
            .username("canhhuu")
            .firstname("Canh")
            .lastname("Pham Huu")
            .password("encodedPassword")
            .dob(dob)
            .build();
  }

  @Test
  void createUser_validRequest_success() {
    // GIVEN
    when(userRepository.existsByUsername(anyString())).thenReturn(false);
    when(userRepository.save(any())).thenReturn(user);

    // WHEN
    ApiResponse<UserResponse> response = userService.createUser(request);

    // THEN
    Assertions.assertThat(response.getCode()).isEqualTo(1000);
    Assertions.assertThat(response.getResult()).isNotNull();
    Assertions.assertThat(response.getResult().getId()).isEqualTo("heheheheheh");
    Assertions.assertThat(response.getResult().getUsername()).isEqualTo("canhhuu");
    Assertions.assertThat(response.getResult().getFirstname()).isEqualTo("Canh");
    Assertions.assertThat(response.getResult().getLastname()).isEqualTo("Pham Huu");
  }

  @Test
  void createUser_userExisted_fail() {
    // GIVEN
    when(userRepository.existsByUsername(anyString())).thenReturn(true);

    // WHEN & THEN
    var exception = assertThrows(AppException.class, () -> userService.createUser(request));

    Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_EXISTED);
  }

  // Hoặc dùng assertThatThrownBy (cách khác):
  @Test
  void createUser_userExisted_fail_alternative() {
    // GIVEN
    when(userRepository.existsByUsername(anyString())).thenReturn(true);

    // WHEN & THEN
    Assertions.assertThatThrownBy(() -> userService.createUser(request))
        .isInstanceOf(AppException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.USER_EXISTED);
  }

  @Test
  @WithMockUser(username = "canhhuu") // Mock user đang đăng nhập
  void getMyInfo_valid_success() {
    // GIVEN
    when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

    // WHEN
    ApiResponse<UserResponse> response = userService.getMyInfo();

    // THEN
    Assertions.assertThat(response.getCode()).isEqualTo(1000);
    Assertions.assertThat(response.getResult()).isNotNull();
    Assertions.assertThat(response.getResult().getUsername()).isEqualTo("canhhuu");
    Assertions.assertThat(response.getResult().getFirstname()).isEqualTo("Canh");
    Assertions.assertThat(response.getResult().getLastname()).isEqualTo("Pham Huu");
  }

  @Test
  @WithMockUser(username = "notexist") // User không tồn tại
  void getMyInfo_userNotFound_fail() {
    // GIVEN
    when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

    // WHEN & THEN
    var exception = assertThrows(AppException.class, () -> userService.getMyInfo());

    Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_NOTEXISTED);
  }
}
