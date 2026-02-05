package com.example.lesson02_DB.config;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.lesson02_DB.entity.User;
import com.example.lesson02_DB.enums.Role;
import com.example.lesson02_DB.repositories.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

  PasswordEncoder passwordEncoder;

  @Bean
  @ConditionalOnProperty(
      prefix = "spring",
      value = "datasource.driver-class-name",
      havingValue = "com.mysql.cj.jdbc.Driver")
  ApplicationRunner applicationRunner(UserRepository userRepo) {
    log.info("CONFIG: Init Application");
    // dc khởi chạy mỗi khi đc start
    return args -> {
      if (userRepo.findByUsername("admin").isEmpty()) {
        var roles = new HashSet<String>(); // HashSet là implementation của Set
        roles.add(Role.ADMIN.name());
        User user =
            User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                // .roles(roles)
                .build();
        userRepo.save(user);
        log.info("admin user has been created with default password: admin, please change it !");
      } else {
        log.info("Admin user already exists");
      }
    };
  }
}
