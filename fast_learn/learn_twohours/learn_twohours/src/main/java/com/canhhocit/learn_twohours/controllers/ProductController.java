package com.canhhocit.learn_twohours.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canhhocit.learn_twohours.Repositories.ProductRepository;
import com.canhhocit.learn_twohours.models.Product;
import com.canhhocit.learn_twohours.models.ResponseObject;

@RestController
@RequestMapping("/api/v1/Products")
public class ProductController {
    // DI = Dependency Injection
    @Autowired
    // đánh dấu đối tượng này được tạo ra ngay khi app được tạo, tạo 1 lần, sau cứ
    // thế dùng -> singleton
    private ProductRepository repo;

    @GetMapping
    List<Product> getAllProducts() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findbyID(@PathVariable Long id) {
        Optional<Product> foundProduct = repo.findById(id);
        // if(foundProduct.isPresent()){// check trong optional nay co ton tai product
        // hay k(check khac null/ check exists)
        // return ResponseEntity.status(HttpStatus.OK).body( new
        // ResponseObject("ok","NGONNNNN", foundProduct));
        // }
        // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new
        // ResponseObject("okn't","cannot find prodcut by id = " + id, ""));
        return foundProduct.isPresent()
                ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "NGONNNNN", foundProduct))
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("okn't", "cannot find prodcut by id = " + id, ""));
    }

    @PostMapping
    ResponseEntity<ResponseObject> insert(@RequestBody Product p) {

        List<Product> founProducts = repo.findByProductName(p.getProductName().trim());
        return founProducts.size() > 0
                ? ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                        .body(new ResponseObject("okn't", "Trùng tên dồii", ""))
                : ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "insert NGONNNNN", repo.save(p)));
    }

    // update + insert = upsert
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> upsert(@RequestBody Product newProduct, @PathVariable Long id) {
        Product savedProduct = repo.findById(id)
                .map(product -> {
                    product.setProductName(newProduct.getProductName());
                    product.setYear(newProduct.getYear());
                    product.setPrice(newProduct.getPrice());
                    product.setUrl(newProduct.getUrl());
                    return repo.save(product);
                })
                .orElseGet(() -> {
                    return repo.save(newProduct);
                });

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Upsert product successfully", savedProduct));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        boolean exists = repo.existsById(id);
        repo.deleteById(id);
        return !exists ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("okn't", "Upsert product not successfully", ""))
                : ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Upsert product successfully", ""));

    }

    @DeleteMapping("/deleteAll")
    ResponseEntity<ResponseObject> deleteALLProduct() {
        List<Product> products = repo.findAll();
        repo.deleteAll();
        return products.size() <= 0 ? ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                new ResponseObject("okn't", "list data =0 ", ""))
                : ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "delete all product successfully", ""));

    }

}
