package com.frank.controller;

import com.frank.model.Transaction;
import com.frank.service.AccountService;
import com.frank.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public TransactionController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("make-transfer")
    public String makeTransfer(Model model){

        //what we need to provide to make transfer happen
        //we need to provide empty transfer object
        model.addAttribute("transaction", Transaction.builder().build());
        //we need all accounts to provide them as sender, receiver
        model.addAttribute("account", accountService.listAllAccount());
        //we need list of transaction(at least 10) to fill table,(business logic is missing)
        model.addAttribute("lastTransaction", transactionService.last10Transactions());


        return "transaction/make-transfer";
    }
}
