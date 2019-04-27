package com.benjamin.eventapi.repository;

import com.benjamin.eventapi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "account", path = "accounts")
public interface AccountRepository extends JpaRepository<Account, Long> {
    public Account findByUsername(String username);
}
