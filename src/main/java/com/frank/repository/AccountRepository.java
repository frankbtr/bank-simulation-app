package com.frank.repository;

import com.frank.enums.AccountType;
import com.frank.exceptions.RecordNotFoundException;
import com.frank.model.Account;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AccountRepository {

    public static List<Account> accountList = new ArrayList<>();

    public Account save(Account account){
        accountList.add(account);
        return account;
    }

    public List<Account> findAll() {
        return accountList;
    }

    public Account findById(UUID id) {
        //write a method that find the account inside the list, if not throws RecordNotFoundException
        return accountList.stream()
                .filter(account -> account.getId().equals(id))
                .findAny().orElseThrow(() -> new RecordNotFoundException("Account not exist in the database"));
    }
}
