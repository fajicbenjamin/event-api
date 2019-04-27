package com.benjamin.eventapi.controller;

import com.benjamin.eventapi.model.Account;
import com.benjamin.eventapi.repository.AccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    private AccountRepository accountRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountController(AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/register")
    public void register(@RequestBody Account user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        accountRepository.save(user);
    }
}
