package com.canhhocit.learn_twohours.models;



import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Product {
    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // seuqence: tạo ra rule khi thêm mới bản ghi
    // 2 cái này có ý nghĩa như nhau
    // @SequenceGenerator(
    //     name="product_sequence",
    //     sequenceName = "product_sequence",
    //     allocationSize = 1// mỗi lần tăng  1
    // )
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    private Long id;
    // validate = contraint
    @Column(nullable = false, unique = true, length = 300)
    private String productName;
    private int year;
    private Double price;
    private String url;
    // caculated field = transient: trường k được lưu ở Db nhưng đc tính từ trường
    // khác(tạm thời)

    @Transient
    int age;
    public int getAge(){
        return Calendar.getInstance().get(Calendar.YEAR) - year;
    }
    public Product(String productName, int year, Double price, String url) {
        this.productName = productName;
        this.year = year;
        this.price = price;
        this.url = url;
    }

}
