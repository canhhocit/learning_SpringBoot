package com.canhhocit.learn01.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canhhocit.learn01.DTO.Request.AccountCreationRequest;
import com.canhhocit.learn01.DTO.Request.AccountUpdateRequest;
import com.canhhocit.learn01.DTO.Response.AccountResponse;
import com.canhhocit.learn01.Entities.Account;
import com.canhhocit.learn01.Exceptions.AppException;
import com.canhhocit.learn01.Exceptions.ErrorCode;
import com.canhhocit.learn01.Mapper.AccountMapper;
import com.canhhocit.learn01.Repositories.AccountRepository;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    AccountRepository acRepo;

    AccountMapper accountMapper;

    public Account createAccount(AccountCreationRequest request) {
        if (acRepo.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        Account ac = accountMapper.toAccount(request);
        return acRepo.save(ac);
    }

    public List<Account> getAllAccount() {
        return acRepo.findAll();
    }

    public AccountResponse getAccount(String username) {
        if (!acRepo.existsByUsername(username)) {
            throw new AppException(ErrorCode.USER_NOTEXISTED);
        }
        return accountMapper.toAccountResponse(acRepo.findByUsername(username));
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

    public AccountResponse updateAccount(AccountUpdateRequest request, String username) {
        if (!acRepo.existsByUsername(username)) {
            throw new AppException(ErrorCode.USER_NOTEXISTED);
        }
        Account ac = acRepo.findByUsername(username);
        accountMapper.updateAccount(ac, request);
        return accountMapper.toAccountResponse(acRepo.save(ac));
    }

}
