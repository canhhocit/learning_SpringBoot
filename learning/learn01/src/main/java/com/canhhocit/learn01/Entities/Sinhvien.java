package com.canhhocit.learn01.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Sinhvien {
    @Id
    private String msv;
    private String hoten;
    private String lop;
    private String sdt;
    
}
