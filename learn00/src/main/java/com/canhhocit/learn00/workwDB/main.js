// URL backend của bạn
        const API_URL = 'http://localhost:8080/api/users';
        
        // Hàm hiển thị thông báo
        function showMessage(text, type) {
            const messageDiv = document.getElementById('message');
            messageDiv.className = 'message ' + type;
            messageDiv.textContent = text;
            setTimeout(() => {
                messageDiv.textContent = '';
                messageDiv.className = '';
            }, 3000);
        }
        
        // 1. TẠO USER MỚI - POST
        async function createUser() {
            const name = document.getElementById('userName').value;
            const address = document.getElementById('userAddress').value;
            
            if (!name || !address) {
                showMessage('Vui lòng nhập đầy đủ thông tin!', 'error');
                return;
            }
            
            try {
                const response = await fetch(API_URL, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ name, address })
                });
                
                if (response.ok) {
                    showMessage('Tạo user thành công!', 'success');
                    clearForm();
                    loadUsers();
                } else {
                    showMessage('Lỗi khi tạo user!', 'error');
                }
            } catch (error) {
                showMessage('Lỗi kết nối: ' + error.message, 'error');
            }
        }
        
        // 2. LẤY DANH SÁCH USER - GET
        async function loadUsers() {
            try {
                const response = await fetch(API_URL);
                const users = await response.json();
                
                const tableBody = document.getElementById('userTable');
                tableBody.innerHTML = '';
                
                if (users.length === 0) {
                    tableBody.innerHTML = '<tr><td colspan="4" style="text-align: center;">Chưa có dữ liệu</td></tr>';
                    return;
                }
                
                users.forEach(user => {
                    const row = `
                        <tr>
                            <td>${user.userID}</td>
                            <td>${user.name}</td>
                            <td>${user.address}</td>
                            <td>
                                <button class="btn-update" onclick="editUser(${user.userID})">Sửa</button>
                                <button class="btn-delete" onclick="deleteUser(${user.userID})">Xóa</button>
                            </td>
                        </tr>
                    `;
                    tableBody.innerHTML += row;
                });
                
                showMessage('Tải dữ liệu thành công!', 'success');
            } catch (error) {
                showMessage('Lỗi khi tải dữ liệu: ' + error.message, 'error');
            }
        }
        
        // 3. CẬP NHẬT USER - PUT
        async function updateUser() {
            const id = document.getElementById('userId').value;
            const name = document.getElementById('userName').value;
            const address = document.getElementById('userAddress').value;
            
            if (!id || !name || !address) {
                showMessage('Vui lòng nhập đầy đủ thông tin (bao gồm ID)!', 'error');
                return;
            }
            
            try {
                const response = await fetch(`${API_URL}/${id}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ name, address })
                });
                
                if (response.ok) {
                    showMessage('Cập nhật thành công!', 'success');
                    clearForm();
                    loadUsers();
                } else {
                    showMessage('Lỗi khi cập nhật!', 'error');
                }
            } catch (error) {
                showMessage('Lỗi kết nối: ' + error.message, 'error');
            }
        }
        
        // 4. XÓA USER - DELETE
        async function deleteUser(id) {
            if (!confirm('Bạn có chắc muốn xóa user này?')) {
                return;
            }
            
            try {
                const response = await fetch(`${API_URL}/${id}`, {
                    method: 'DELETE'
                });
                
                if (response.ok) {
                    showMessage('Xóa thành công!', 'success');
                    loadUsers();
                } else {
                    showMessage('Lỗi khi xóa!', 'error');
                }
            } catch (error) {
                showMessage('Lỗi kết nối: ' + error.message, 'error');
            }
        }
        
        // Hàm load thông tin user vào form để sửa
        async function editUser(id) {
            try {
                const response = await fetch(`${API_URL}/${id}`);
                const user = await response.json();
                
                document.getElementById('userId').value = user.userID;
                document.getElementById('userName').value = user.name;
                document.getElementById('userAddress').value = user.address;
                
                showMessage('Đã load thông tin user. Sửa và nhấn "Cập nhật"', 'success');
            } catch (error) {
                showMessage('Lỗi khi load user: ' + error.message, 'error');
            }
        }
        
        // Xóa form
        function clearForm() {
            document.getElementById('userId').value = '';
            document.getElementById('userName').value = '';
            document.getElementById('userAddress').value = '';
        }
        
        // Tự động load danh sách khi vào trang
        window.onload = function() {
            loadUsers();
        };