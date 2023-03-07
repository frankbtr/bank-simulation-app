package com.frank.repository;

import com.frank.enums.AccountType;
import com.frank.model.Account;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ComponentScan
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
        return null;
        //use stream
    }
}
