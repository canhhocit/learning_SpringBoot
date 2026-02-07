
package com.example.lesson02_DB.config;// NOSONAR
import org.springframework.web.cors.CorsConfigurationSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
// author with method
@EnableMethodSecurity
public class SecurityConfig {

  private static final String[] ENDPOINTS_LIST = {
      "/users", "/auth/token", "/auth/introspect", "/auth/logout", "/auth/refresh"
  };

  @Autowired
  private CustomJwtDecoder customJwtDecoder; // NOSONAR

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.addAllowedOriginPattern("*");
    // config.addAllowedOrigin("http://localhost:3000");
    config.addAllowedMethod("*");
    config.addAllowedHeader("*");
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .cors(cors -> {
        })
        .csrf(AbstractHttpConfigurer::disable);
    // cho phép endpoint này được thực thi khi chưa JWT
    httpSecurity.authorizeHttpRequests(
        request -> request
            .requestMatchers(HttpMethod.POST, ENDPOINTS_LIST)
            .permitAll()
            // .requestMatchers(HttpMethod.POST, "/identity/users").permitAll()
            // author with endpoint
            // .requestMatchers(HttpMethod.GET,"/users")
            // .hasAuthority("ROLE_ADMIN")
            // .hasRole(Role.ADMIN.name())
            .anyRequest()
            .authenticated());

    // dki 1 provider manager(authentication provider) -> support JWT(inject để
    // authen(validate))
    // lúc này có thể dán token để unlock các method Get/post/put/delete chưa được
    // mở khóa mặc định
    httpSecurity.oauth2ResourceServer(
        oauth2 -> oauth2
            .jwt(
                jwtConfigurer -> jwtConfigurer
                    .decoder(customJwtDecoder)
                    .jwtAuthenticationConverter(jwtAuthenticationConverter()))
            .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));

    // tắt csrf
    // httpSecurity.csrf(httpSecurityCsrfConfigurer ->
    // httpSecurityCsrfConfigurer.disable());
    // rút gọn
    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    return httpSecurity.build();
  }

  @Bean
  JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }

  // // decoder là Interface nên cần implement
  // @Bean
  // JwtDecoder jwtDecoder() {
  // SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(),
  // "HS512");
  // return NimbusJwtDecoder
  // .withSecretKey(secretKeySpec)
  // .macAlgorithm(MacAlgorithm.HS512)
  // .build();
  // }

  // config để dùng nhiều nơi
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }
}
