# Hướng dẫn sử dụng Spotless

Spotless đã được cấu hình trong project để tự động format code Java theo Google Java Style Guide.

## Các lệnh Spotless

### 1. Kiểm tra format
```bash
mvn spotless:check
```
Kiểm tra xem code có đúng format chưa (không thay đổi file).

### 2. Tự động format code
```bash
mvn spotless:apply
```
Tự động format tất cả file Java trong project.

### 3. Format trong quá trình build
Spotless đã được cấu hình tự động chạy khi bạn build project:
```bash
mvn clean install
```
### 4. Format on/off trong code
```bash
  //spotless:off
  code
  //spotless:on
```


## Tính năng của Spotless đã cấu hình

✅ **Google Java Format** - Format code theo chuẩn Google  
✅ **Remove Unused Imports** - Xóa các import không dùng  
✅ **Import Order** - Sắp xếp import theo thứ tự: java → javax → jakarta → org → com  
✅ **End with Newline** - Đảm bảo file kết thúc bằng dòng trống  
✅ **Trim Trailing Whitespace** - Xóa khoảng trắng thừa cuối dòng  
✅ **XML Formatting** - Format cả file XML (pom.xml, config files)

## Lưu ý

- Spotless sẽ tự động check format mỗi khi bạn compile code
- Nếu code chưa đúng format, build sẽ fail và yêu cầu bạn chạy `mvn spotless:apply`
- Nên chạy `mvn spotless:apply` trước khi commit code

## Tips

Thêm vào Git hooks để tự động format trước khi commit:
```bash
# .git/hooks/pre-commit
#!/bin/sh
mvn spotless:apply
git add -u
```
