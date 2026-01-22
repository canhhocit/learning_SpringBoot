package com.canhhocit.learn01.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canhhocit.learn01.Entities.Sinhvien;


@Repository
public interface SinhvienRepository extends JpaRepository<Sinhvien,String>{
    boolean existsBySdt(String sdt);
    boolean existsByMsv(String msv);
}
