package com.canhhocit.learn00.workwDB;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
/*
// Cho phép TẤT CẢ domain (dùng khi dev/test)
@CrossOrigin(origins = "*")

// Chỉ cho phép 1 domain cụ thể (an toàn hơn khi production)
@CrossOrigin(origins = "http://localhost:3000")

// Cho phép nhiều domain
@CrossOrigin(origins = {"http://localhost:3000", "https://myapp.com"})

// Cho phép các method cụ thể
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
*/
public class UserController {
    private final UserRepository repo;

    @PostMapping
    public User create(@RequestBody User u) {
        return repo.save(u);
    }

    @GetMapping
    public List<User> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public User getUserbyID(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User newUser) {
        User oldUser = repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        oldUser.setName(newUser.getName());
        oldUser.setAddress(newUser.getAddress());

        return repo.save(oldUser);
    }

}
