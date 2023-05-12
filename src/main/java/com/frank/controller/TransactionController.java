package com.frank.controller;

import com.frank.dto.AccountDTO;
import com.frank.dto.TransactionDTO;
import com.frank.service.AccountService;
import com.frank.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

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
        model.addAttribute("transaction", TransactionDTO.builder().build());
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
    public String postMakeTransfer(@Valid @ModelAttribute("transaction") TransactionDTO transactionDTO, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("accounts", accountService.listAllAccount());
            return "transaction/make-transfer";
        }


        // I have UUID and accounts, but I need to provide Account object!
        // I need to find Accounts based on the ID that I have and use a parameters to makeTransfer
        AccountDTO sender = accountService.retrieveById(transactionDTO.getSender());
        AccountDTO receiver = accountService.retrieveById(transactionDTO.getReceiver());
        transactionService.makeTransfer(sender, receiver, transactionDTO.getAmount(), new Date(), transactionDTO.getMessage());

        return "redirect:/make-transfer";
    }

    //Write a method, that gets the account from index.html and print on the console
    //(work on index.html and here)
    //transaction/{Id}
    // return transaction/transaction page
    @GetMapping("/transaction/{id}")
    public String getTransactionList(@PathVariable("id") UUID id, Model model){

        System.out.println(id);
        //get the list of transaction based on id and return as a model attribute
        //Task - Complete the method (service and repository)
        //findTransactionListById
        model.addAttribute("transactions", transactionService.findTransactionListById(id));


        return "transaction/transactions";
    }



}
