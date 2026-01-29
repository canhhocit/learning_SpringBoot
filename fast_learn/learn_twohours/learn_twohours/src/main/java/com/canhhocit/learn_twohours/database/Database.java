package com.canhhocit.learn_twohours.database;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.canhhocit.learn_twohours.Repositories.ProductRepository;
// import com.canhhocit.learn_twohours.models.Product;

@Configuration
// chứa các bean method: đc gọi ngay khi được chạy
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        // logger
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                // Product pA = new Product("MacBook Pro 16", 2020, 2400.0, "");
                // Product pB = new Product("window 22", 2022, 1800.0, "");
                // System.out.println("insert: " + productRepository.save(pA));
                // System.out.println("insert: " + productRepository.save(pB));
                // loger.info = sout
                // logger.info("insert: " + productRepository.save(pA));
                // logger.info("insert: " + productRepository.save(pB));
              
            }

        };
    }
}
