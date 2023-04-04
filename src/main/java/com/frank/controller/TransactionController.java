package com.frank.controller;

import com.frank.model.Account;
import com.frank.model.Transaction;
import com.frank.service.AccountService;
import com.frank.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class TransactionController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public TransactionController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/make-transfer")
    public String makeTransfer(Model model){

        //what we need to provide to make transfer happen
        //we need to provide empty transfer object
        model.addAttribute("transaction", Transaction.builder().build());
        //we need all accounts to provide them as sender, receiver
        model.addAttribute("accounts", accountService.listAllAccount());
        //we need list of transaction(at least 10) to fill table,(business logic is missing)
        model.addAttribute("lastTransactions", transactionService.last10Transactions());

        return "transaction/make-transfer";
    }

    //Task
    //Write a post method, that takes transaction object from the method above
    //complete the make transfer and return the message
    @PostMapping("/transfer")
    public String postMakeTransfer(@ModelAttribute("transaction") Transaction transaction){

        // I have UUID and accounts, but I need to provide Account object!
        // I need to find Accounts based on the ID that I have and use a parameters to makeTransfer
        Account sender = accountService.retrieveById(transaction.getSender());
        Account receiver = accountService.retrieveById(transaction.getReceiver());
        transactionService.makeTransfer(sender, receiver, transaction.getAmount(), new Date(), transaction.getMessage());

        return "redirect:/make-transfer";
    }
}
