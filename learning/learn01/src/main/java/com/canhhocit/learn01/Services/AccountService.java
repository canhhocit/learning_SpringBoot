package com.canhhocit.learn01.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canhhocit.learn01.DTO.Request.AccountCreationRequest;
import com.canhhocit.learn01.DTO.Request.AccountUpdateRequest;
import com.canhhocit.learn01.Entities.Account;
import com.canhhocit.learn01.Exceptions.AppException;
import com.canhhocit.learn01.Exceptions.ErrorCode;
import com.canhhocit.learn01.Repositories.AccountRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountService {
    @Autowired
    AccountRepository acRepo;

    public Account createAccount(AccountCreationRequest request) {
        Account ac = new Account();
        if (acRepo.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        ac.setUsername(request.getUsername());
        ac.setPassword(request.getPassword());
        return acRepo.save(ac);
    }

    public List<Account> getAllAccount() {
        return acRepo.findAll();
    }

    public Account getAccount(String username) {
        if (!acRepo.existsByUsername(username)) {
            throw new AppException(ErrorCode.USER_NOTEXISTED);
        }
        return acRepo.findByUsername(username);
    }

    @Transactional
    // mở giao dịch DB, ins,upd,del đều cần,bên trên do JPA làm hộ r. do dây là hàm
    // tự viết thêm
    public String deleteAccount(String username) {
        if (!acRepo.existsByUsername(username)) {
            throw new AppException(ErrorCode.USER_NOTEXISTED);
        }
        acRepo.deleteByUsername(username);

        return "account deleted!";

    }

    public Account updateAccount(AccountUpdateRequest request, String username) {
        Account ac = getAccount(username);
        ac.setPassword(request.getPassword());
        return acRepo.save(ac);
    }

}
