package com.frank.service;

import com.frank.dto.AccountDTO;
import com.frank.enums.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {

    void createNewAccount(AccountDTO accountDTO);

    List<AccountDTO> listAllAccount();

    void deleteAccount(Long id);

    void activateAccount(Long id);

    AccountDTO retrieveById(Long Id);

    List<AccountDTO> listAllActiveAccounts();

    void updateAccount(AccountDTO dto);
}
