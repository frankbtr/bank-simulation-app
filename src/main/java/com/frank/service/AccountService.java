package com.frank.service;

import com.frank.dto.AccountDTO;
import com.frank.enums.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountDTO createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId);

    List<AccountDTO> listAllAccount();

    void deleteAccount(UUID id);

    void activateAccount(UUID id);

    AccountDTO retrieveById(UUID Id);
}
