package com.frank.controller;

import com.frank.dto.AccountDTO;
import com.frank.enums.AccountType;
import com.frank.service.AccountService;
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
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/index")
    public String getIndex(Model model){

        model.addAttribute("accountList", accountService.listAllAccount());
        return "account/index";
    }

    @GetMapping("/create-form")
    public String createAccount(Model model){
        //empty account object provided
        model.addAttribute("account", AccountDTO.builder().build());
        //accountType enum needs to fill dropdown
        model.addAttribute("accountTypes", AccountType.values());

        return "account/create-account";
    }

    @PostMapping("/create")
    public String createAccount(@Valid @ModelAttribute("account") AccountDTO accountDTO, BindingResult bindingResult, Model model ){

        if (bindingResult.hasErrors()){

            model.addAttribute("accountTypes", AccountType.values());
            return "account/create-account";
        }

        System.out.println(accountDTO);
        accountService.createNewAccount(accountDTO.getBalance(), new Date(), accountDTO.getAccountType(), accountDTO.getUserId());
        return "redirect:/index";
    }


    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id") UUID id){
        System.out.println(id);

        //trigger to delete account method
        accountService.deleteAccount(id);
        return "redirect:/index";
    }

    @GetMapping("/activate/{id}")
    public String activateAccount(@PathVariable("id") UUID id){
        System.out.println(id);
        accountService.activateAccount(id);
        return "redirect:/index";
    }
}
