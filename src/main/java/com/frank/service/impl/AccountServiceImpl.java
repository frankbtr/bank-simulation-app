package com.frank.service.impl;

import com.frank.dto.AccountDTO;
import com.frank.enums.AccountStatus;
import com.frank.enums.AccountType;
import com.frank.mapper.AccountMapper;
import com.frank.repository.AccountRepository;
import com.frank.service.AccountService;
import org.springframework.stereotype.Component;

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
