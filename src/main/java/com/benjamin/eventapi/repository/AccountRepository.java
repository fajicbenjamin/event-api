package com.benjamin.eventapi.repository;

import com.benjamin.eventapi.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "account", path = "accounts")
public interface AccountRepository extends CrudRepository<Account, Long> {
    public Account findByUsername(String username);
}
