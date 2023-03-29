package com.frank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionController {


    @GetMapping("make-transfer")
    public String makeTransfer(){

        //what we need to provide to make transfer happen
        //we need to provide empty transfer object
        //we need all accounts to provide them as sender, receiver
        //we need list of transaction(at least 10) to fill table,(business logic is missing)
        return "transaction/make-transfer";
    }
}
