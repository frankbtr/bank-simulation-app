package com.frank.service;

import com.frank.enums.AccountType;
import com.frank.model.Account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public interface AccountService {

    Account createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId);

    List<Account> listOfAccount();
}
