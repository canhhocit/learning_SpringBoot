package com.example.lesson02_DB.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// khai bao anotation
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
  // String message() default "Invalid date of birth";
  String message() default "{jakarta.validation.constraints.Size.message}";

  int min();

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
