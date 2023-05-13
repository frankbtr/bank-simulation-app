package com.frank.service.impl;

import com.frank.dto.AccountDTO;
import com.frank.dto.TransactionDTO;
import com.frank.enums.AccountType;
import com.frank.exceptions.AccountOwnershipException;
import com.frank.exceptions.BadRequestException;
import com.frank.exceptions.BalanceNotSufficientException;
import com.frank.exceptions.UnderConstructionException;
import com.frank.repository.AccountRepository;
import com.frank.repository.TransactionRepository;
import com.frank.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    public TransactionDTO makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message) {

        if (!underConstruction) {
            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);
        /*
            after all validations are completed, and money is transferred, we need to  create Transaction object and save/return it.
         */

            TransactionDTO transactionDTO = new TransactionDTO();

            return transactionRepository.save(transactionDTO);

        } else {
            throw new UnderConstructionException("App is under construction,try again later.");
        }


    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver) {
        if (checkSenderBalance(sender, amount)) {
            //update sender and receiver balance
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));

        } else {
            throw new BalanceNotSufficientException("Balance is not enough for this transfer.");
        }

    }

    private boolean checkSenderBalance(AccountDTO sender, BigDecimal amount) {
        //verify sender has enough balance to send
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private void checkAccountOwnership(AccountDTO sender, AccountDTO receiver) {
        /*
            write an if statement that checks if one of the account is saving,
            and user of sender or receiver is not the same, throw AccountOwnershipException
         */

        if ((sender.getAccountType().equals(AccountType.SAVING) || receiver.getAccountType().equals(AccountType.SAVING))
                && !sender.getUserId().equals(receiver.getUserId())) {
            throw new AccountOwnershipException("Since you are using a savings account, the sender and receiver userId must be the same.");
        }

    }

    private void validateAccount(AccountDTO sender, AccountDTO receiver) {
        /*
            -if any of the account is null
            -if account ids are the same(same account)
            -if the accounts exist in the database(repository)
         */

        if (sender == null || receiver == null) {
            throw new BadRequestException("Sender or Receiver cannot be null");
        }

        if (sender.getId().equals(receiver.getId())) {
            throw new BadRequestException("Sender account needs to be different than receiver");
        }
        //verify if we have sender and receiver in the database
        findAccountById(sender.getId());
        findAccountById(receiver.getId());


    }

    private void findAccountById(Long id) {
        accountRepository.findById(id);
    }

    @Override
    public List<TransactionDTO> findAllTransaction() {
        return transactionRepository.findAll();
    }

    @Override
    public List<TransactionDTO> last10Transactions() {
        //we want last 10 latest transaction
        return transactionRepository.findLast10Transaction();
    }

    @Override
    public List<TransactionDTO> findTransactionListById(Long id) {
        return transactionRepository.findTransactionListById(id);
    }

}
