package com.canhhocit.learn01.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canhhocit.learn01.DTO.Request.AccountCreationRequest;
import com.canhhocit.learn01.DTO.Request.AccountUpdateRequest;
import com.canhhocit.learn01.DTO.Request.ApiResponse;
import com.canhhocit.learn01.DTO.Response.AccountResponse;
import com.canhhocit.learn01.Entities.Account;
import com.canhhocit.learn01.Services.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/acc")
public class AccountController {
    @Autowired
    private AccountService acService;

    @PostMapping
    ApiResponse<Account> createAcc(@RequestBody @Valid AccountCreationRequest request) {
        ApiResponse<Account> apiResponse = new ApiResponse<>();

        apiResponse.setResult(acService.createAccount(request));
        return apiResponse;
    }

    @GetMapping
    List<Account> getAlList() {
        return acService.getAllAccount();
    }

    @GetMapping("/{username}")
    AccountResponse getAccount(@PathVariable String username) {
        return acService.getAccount(username);
    }

    @PutMapping("/{username}")
    AccountResponse updateAccount(@RequestBody @Valid AccountUpdateRequest request, @PathVariable String username) {
        return acService.updateAccount(request, username);
    }

    @DeleteMapping("/{username}")
    String delAcc(@PathVariable String username) {
        return acService.deleteAccount(username);

    }
}
