package com.canhhocit.learn_twohours.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canhhocit.learn_twohours.models.Product;

public interface ProductRepository extends JpaRepository<Product,Long>  {

    List<Product> findByProductName(String productName);
} 