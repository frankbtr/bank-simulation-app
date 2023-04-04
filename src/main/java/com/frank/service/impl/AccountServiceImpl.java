package com.frank.service.impl;

import com.frank.enums.AccountStatus;
import com.frank.enums.AccountType;
import com.frank.model.Account;
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
    public Account createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId) {
        //we need to create Account object;
        Account account = Account.builder().id(UUID.randomUUID())
                .userId(userId).balance(balance).accountType(accountType).creationDate(creationDate)
                .accountStatus(AccountStatus.ACTIVE).build();
        //save into the database(repository)
        //return the object created
        return accountRepository.save(account);
    }

    @Override
    public List<Account> listAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccount(UUID id) {
        //find the account object based on id
        Account account = accountRepository.findById(id);

        //update the account status from active to deleted
        account.setAccountStatus(AccountStatus.DELETED);
    }

    @Override
    public void activateAccount(UUID id) {
        Account account = accountRepository.findById(id);
        account.setAccountStatus(AccountStatus.ACTIVE);
    }

    @Override
    public Account retrieveById(UUID Id) {
        return accountRepository.findById(Id);
    }
}
