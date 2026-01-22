package com.canhhocit.learn00.Basic;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("api/products")
public class ProductController {
    @GetMapping
    public String getAllProducts() {
        return "List of bananas";
    }
    @PostMapping
    public String createProduct(){
        return "created";
    }
    
}
