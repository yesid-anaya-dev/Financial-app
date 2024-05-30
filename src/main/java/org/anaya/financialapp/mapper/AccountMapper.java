package org.anaya.financialapp.mapper;

import org.anaya.financialapp.domain.model.Account;
import org.anaya.financialapp.dto.AccountRequest;
import org.anaya.financialapp.dto.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "client", ignore = true)
    Account toAccount(AccountRequest accountRequest);

    AccountResponse toAccountResponse(Account account);
}
