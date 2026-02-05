package com.example.lesson02_DB.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

// VERTIFY TOKEN
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IntrospectRequest {
  String token;
}
