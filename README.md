# 🚀 Spring Boot: Hành trình vượt khó
## 10/01/2026 - 10/04/2026

### Beginer -> Master

-- write once, run any where --

==================================================================  

Xây dựng theo kiến trúc cố định:

•	Controller -> Service -> Repository

•	REST API

•	DTO, Validation

=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-==-=-=-=-=-=-=-=-=  

## Đã học:
- Các anotaion cơ bản
- Quản lý exception tập trung ExceptionHadling
- hash pass bằng thuật toán Bcrypt
- tạo, Authenticate = JWT
- REsful API (CRUD cơ bản)
- Validation cơ bản
- Sử dụng thư viện lombok @ Mapstruct -> clean code;
- FE: lấy api cơ bản
## Hướng tới & chưa học:
- Authorization: phân quyền với PreAutho và PostAutho
- Kiến trúc Spring Sercurity
- UnitTest
- deploy Docker
- ...
  
------------------------------------- END  -------------------------------------
  
___________________________________________________________________________________________  
# iead personal Project:

  1.Cơ bản: Hệ thống Quản lý Thư viện (Library Management)
  
Tính năng: Thêm/Sửa/Xóa (CRUD) sách, quản lý người mượn, tìm kiếm sách theo thể loại/tác giả.

Kỹ thuật:

 Database (MySQL).
 
 Xây dựng RESTful API.
 
 Validation

2.Trung bình: Ứng dụng Bán hàng/Thương mại điện tử Mini

Tính năng: Đăng ký/Đăng nhập, giỏ hàng, đặt hàng , lọc sản phẩm theo giá/danh mục.

Kỹ thuật :

 Spring Security + JWT: đăng nhập và phân quyền.
 
 Mapping Entities: One-to-Many, Many-to-Many.
 
 Exception Handling: Xử lý lỗi tập trung bằng @ControllerAdvice.
 

3.Nâng cao: Hệ thống Đặt chỗ (Booking System)

Tính năng: Đặt vé xem phim/phòng khách sạn, gửi email xác nhận tự động, bình luận/đánh giá.

Kỹ thuật:

 Spring Boot Email: Gửi mail thông báo khi đặt hàng thành công.
 
 Swagger/OpenAPI
 
 Unit Test
 
 ___________________________________________________________________________________________  
 # Note:
### Nên thay @autowire bằng:

- `@RequiredArgsConstructor`: tạo constructor cho all các biến khai báo là final

- '@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)' : tự động đưa các field không khai báo trở thành kiểu private final.
