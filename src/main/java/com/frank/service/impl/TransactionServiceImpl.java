package com.frank.service.impl;

import com.frank.enums.AccountType;
import com.frank.exceptions.AccountOwnershipException;
import com.frank.exceptions.BadRequestException;
import com.frank.model.Account;
import com.frank.model.Transaction;
import com.frank.repository.AccountRepository;
import com.frank.service.TransactionService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;

    public TransactionServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {
        /*
            -if sender or receiver is null?
            -if sender and receiver is the same account?
            -if sender has enough balance?
            -if both accounts are checking, if not, one of them saving, it needs to be same userId
         */
        validateAccount(sender, receiver);
        checkAccountOwnership(sender, receiver);


        return null;

    }

    private void checkAccountOwnership(Account sender, Account receiver) {
        /*
            write an if statement checks if one of the account is saving,
            and user of sender and receiver is not the same, throw AccountOwnershipException
         */

        // a logic like:
//        if(oneOfTheAccountIsSaving && userIdIsDifferent)
        if ((sender.getAccountType().equals(AccountType.SAVING) || receiver.getAccountType().equals(AccountType.SAVING))
        && !sender.getUserId().equals(receiver.getUserId())){
            throw new AccountOwnershipException("Since you are using a saving account, the sender and receiver userId must be the same!");
        }
    }

    private void validateAccount(Account sender, Account receiver){
        /*
            -if any of the account is null
            -if account ids are the same(account)
            -if the account exist in the database(repository)
         */

        if (sender==null || receiver==null){
            throw new BadRequestException("Sender or Receiver cannot be null");
        }

        if (sender.getId().equals(receiver.getId())){
            throw new BadRequestException("Sender account needs to be different than Receiver");
        }

        findAccountById(sender.getId());
        findAccountById(receiver.getId());

    }

    private void findAccountById(UUID id) {
    accountRepository.findById(id);
    }

    @Override
    public List<Transaction> findAllTransaction() {
        return null;
    }
}
