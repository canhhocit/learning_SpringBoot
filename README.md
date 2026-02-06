  # Spring Boot: Hành trình vượt khó

  ----- `Backend Java thiên hướng mở rộng FullStack` -----

## 10/01/2026 - 10/04/2026

### Beginer -> Master


-- `write once, run any where` --

==================================================================

Xây dựng theo kiến trúc:

• Controller -> Service -> Repository

• REST API

• DTO, Validation

=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-==-=-=-=-=-=-=-=-=

## Đã học:

- Các anotaion cơ bản
- Quản lý exception tập trung ExceptionHadling
- hash pass bằng thuật toán Bcrypt
- JWT: Json web token: tạo & xác thực:
- REsful API (CRUD cơ bản)
- Validation cơ bản
- Sử dụng thư viện lombok @ Mapstruct -> clean code;
- Kiến trúc Spring Sercurity với cấu hình JWT -> authentication
- Upload file
- Refresh Token: user: requestAPI(token) --> bakcend: (token invalid)
          BE --new Token--> client --> requestAPI(new token) --->BE(ok)
- Authorization: phân quyền với PreAutho và PostAutho
- UnitTest : sử dụng anotation `@SpringBootTest` &`@Test` // `Nguyên tắc`: Viết layer trên thì Moc layer dưới

- Đo code với JaCoCo 
- sử dụng SonarLint và SonarQube để quét & bug và lỗi cảnh báo


- FE: lấy api cơ bản
## Hướng tới & chưa học:

- Build Docker image
- aws
- swagger
  #### Học FE ở múc đủ dùng:
  - React cơ bản

  - Call API

  - Login + token
  `MỤC ĐÍCH`: hiểu FE

------------------------------------- END ----------------------------------------

## `NOTE`: Các kiến thức cần học thêm:

- AWS, Docker,k8s
- deployment
- Tối ưu hệ thống
- ...
- Tìm hiểu n8n

### JWT

- JWT gồm 3 phần: header(chứa in4 về loại token & thuật toán để ký token) ,
  payload(chứa nội dung token), signature(hash header - payload)
  --
  ![image](https://github.com/canhhocit/learning_SpringBoot/blob/main/JWT.png)

- header sử dụng thuật toán : HS512 -- JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

- `https://generate-random.org/encryption-keys` : link đây đỡ phải tìm. generate chuỗi 32 bytes=256 bits cho thuật toán MACSigner(ký token)

- `https://www.jwt.io/` : test token

### Security

- POST hay bị chặn còn GET thì lọt khi dùng Spring Security => phải cấu hình cho các endpoint nào cho phép sử dụng khi chưa có token.
  Spring Security bật CSRF mặc định.

GET → được coi là safe method → KHÔNG cần CSRF token

POST / PUT / DELETE → BẮT BUỘC có CSRF token

###### Test postman = cách chọn Bearer Token

---

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

Exception Handling: Xử lý lỗi tập trung bằng @ControllerAdvice.// riêng 401 thì đặc biệt, phải config để bắt

3.Nâng cao: Hệ thống Đặt chỗ (Booking System)

Tính năng: Đặt vé xem phim/phòng khách sạn, gửi email xác nhận tự động, bình luận/đánh giá.

Kỹ thuật:

Spring Boot Email: Gửi mail thông báo khi đặt hàng thành công, gửi mail về admin khi có người đặt hàng xong(mail 2 chiều).

Swagger/OpenAPI

Unit Test

---

---

# Note:

### Nên thay @autowire bằng: `@RequiredArgsConstructor` + `@FieldDefaults` do lombok sẽ generate constructor và biến đó sẽ được inject vào đó

- `@RequiredArgsConstructor`: tạo constructor cho all các biến khai báo là final

- `@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)` : tự động đưa các field không khai báo trở thành kiểu private final.

- `@JsonInclude(JsonInclude.Include.NON_NULL)`: Khai báo cho Json biết là nếu field nào null thì k ghi vào json

