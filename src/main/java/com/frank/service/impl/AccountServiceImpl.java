package com.frank.service.impl;

import com.frank.dto.AccountDTO;
import com.frank.enums.AccountStatus;
import com.frank.enums.AccountType;
import com.frank.repository.AccountRepository;
import com.frank.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class AccountServiceImpl implements AccountService {

    //it is a good practice use private final!
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDTO createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId) {
        //we need to create Account object;
        AccountDTO accountDTO = AccountDTO.builder().id(UUID.randomUUID())
                .userId(userId).balance(balance).accountType(accountType).creationDate(creationDate)
                .accountStatus(AccountStatus.ACTIVE).build();
        //save into the database(repository)
        //return the object created
        return accountRepository.save(accountDTO);
    }

    @Override
    public List<AccountDTO> listAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccount(Long id) {
        //find the account object based on id
        AccountDTO accountDTO = accountRepository.findById(id);

        //update the account status from active to deleted
        accountDTO.setAccountStatus(AccountStatus.DELETED);
    }

    @Override
    public void activateAccount(Long id) {
        AccountDTO accountDTO = accountRepository.findById(id);
        accountDTO.setAccountStatus(AccountStatus.ACTIVE);
    }

    @Override
    public AccountDTO retrieveById(Long Id) {
        return accountRepository.findById(Id);
    }
}
