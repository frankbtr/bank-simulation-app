package com.frank.service.impl;

import com.frank.enums.AccountType;
import com.frank.exceptions.AccountOwnershipException;
import com.frank.exceptions.BadRequestException;
import com.frank.exceptions.BalanceNotSufficientException;
import com.frank.exceptions.UnderConstructionException;
import com.frank.model.Account;
import com.frank.model.Transaction;
import com.frank.repository.AccountRepository;
import com.frank.repository.TransactionRepository;
import com.frank.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {


    @Value("${under_construction}")
    private boolean underConstruction;

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {

        if (!underConstruction){
            /*
            -if sender or receiver is null?
            -if sender and receiver is the same account?
            -if sender has enough balance?
            -if both accounts are checking, if not, one of them saving, it needs to be same userId
         */
            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);
        /*
            after all validations are completed, and money is transferred, we need to create Transaction object and save/return it
         */

            Transaction transaction = Transaction.builder().amount(amount).sender(sender.getId())
                    .receiver(receiver.getId()).creationDate(creationDate).message(message).build();
            return transactionRepository.save(transaction);
        }else {
            throw new UnderConstructionException("App is under construction");
        }
    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) {
        if (checkSenderBalance(sender, amount)){
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        }else{
            throw new BalanceNotSufficientException("Balance is not enough for this transfer");
        }
    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {
        // verify sender has enough balance to send
        return  sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private void checkAccountOwnership(Account sender, Account receiver) {
        /*
            write an if statement checks if one of the account is saving,
            and user of sender and receiver is not the same, throw AccountOwnershipException
         */

        // a logic like:
//        if(oneOfTheAccountIsSaving && userIdIsDifferent)
        if((sender.getAccountType().equals(AccountType.SAVING)||receiver.getAccountType().equals(AccountType.SAVING))
                && !sender.getUserId().equals(receiver.getUserId())){
            throw new AccountOwnershipException("Since you are using a savings account, the sender and receiver userId must be the same.");
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
        return transactionRepository.findAll();
    }
}
