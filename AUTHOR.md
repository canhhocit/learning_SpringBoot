# authorization with Method into SERVICES

- `@PreAuthorize("hasRole('ADMIN')")` : ktra trước lúc gọi hàm có role là ADMIN thì mới gọi hàm else -> cancle
- `@PostAuthorize("hasRole('ADMIN')")`: inject sau khi method đc thực hiện xong, nếu k thỏa mãn đk thì method sẽ bị chặn lại

   + `@PostAuthorize("returnObject.username == authentication.name")` : ktra username nếu đúng là user đang đăng nhập thì cho phép// chỉ lấy được thông tin của chính mình