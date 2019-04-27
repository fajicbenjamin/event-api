package com.benjamin.eventapi.controller;

import com.benjamin.eventapi.model.Account;
import com.benjamin.eventapi.repository.AccountRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
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

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedAccountsTable(accountRepository);
    }

    private void seedAccountsTable(AccountRepository accountRepository) {
        Account account = new Account();
        account.setUsername("admin");
        account.setEmail("admin@admin.com");
        account.setAdmin(true);
        account.setPassword(new BCryptPasswordEncoder().encode("secret"));
        accountRepository.save(account);
    }
}
