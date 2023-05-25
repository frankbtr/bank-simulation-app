package com.frank.service.impl;

import com.frank.dto.AccountDTO;
import com.frank.entity.Account;
import com.frank.enums.AccountStatus;
import com.frank.enums.AccountType;
import com.frank.mapper.AccountMapper;
import com.frank.repository.AccountRepository;
import com.frank.service.AccountService;
import org.springframework.stereotype.Component;

import javax.persistence.Convert;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountServiceImpl implements AccountService {

    //it is a good practice use private final!
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public void createNewAccount(AccountDTO accountDTO) {

        //we will complete DTO first
        accountDTO.setAccountStatus(AccountStatus.ACTIVE);
        accountDTO.setCreationDate(new Date());

        //convert it to entity and save it
        accountRepository.save(accountMapper.convertToEntity(accountDTO));


    }

    @Override
    public List<AccountDTO> listAllAccount() {

        /*
            we are getting list of account from repo(database
            but we need to return list of dto to controller
            we need the mapper to convert them
         */
        return accountRepository.findAll().stream().map(accountMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        //find the account object based on id
        Account account = accountRepository.findById(id).get();

        //update the account status from active to deleted
        account.setAccountStatus(AccountStatus.DELETED);
    }

    @Override
    public void activateAccount(Long id) {
        Account account = accountRepository.findById(id).get();
        account.setAccountStatus(AccountStatus.ACTIVE);
    }

    @Override
    public AccountDTO retrieveById(Long Id) {
        return accountMapper.convertToDTO(accountRepository.findById(Id).get());
    }

    @Override
    public List<AccountDTO> listAllActiveAccounts() {
        //We need active accounts from repository
        List<Account> accountList = accountRepository.findAllByAccountStatus(AccountStatus.ACTIVE);


        return accountList.stream().map(accountMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void updateAccount(AccountDTO accountDTO) {
        accountRepository.save(accountMapper.convertToEntity(accountDTO));
    }
}
