package com.canhhocit.learn00.workwDB;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    // @GeneratedValue (strategy = GenerationType.IDENTITY)
    // private long userID;
    @GeneratedValue (strategy = GenerationType.UUID)// id type long => String
    private String userID;
    private String name;
    private String address;
}
