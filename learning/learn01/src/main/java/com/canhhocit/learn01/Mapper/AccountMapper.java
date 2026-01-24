package com.canhhocit.learn01.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.canhhocit.learn01.DTO.Request.AccountCreationRequest;
import com.canhhocit.learn01.DTO.Request.AccountUpdateRequest;
import com.canhhocit.learn01.DTO.Response.AccountResponse;
import com.canhhocit.learn01.Entities.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "id", ignore = true)
    // Nhận về param creation request trả về class acc
    Account toAccount(AccountCreationRequest request);

    // MappingTarget: map data tu accountupdate request
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    /*
      @BeanMapping(ignoreByDefault = true)
      @Mapping(target = "password", source = "password")
      map tung field
     */
    void updateAccount(@MappingTarget Account ac, AccountUpdateRequest request);

    AccountResponse toAccountResponse(Account ac);
}
