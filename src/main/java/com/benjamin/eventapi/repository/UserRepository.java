package com.benjamin.eventapi.repository;

import com.benjamin.eventapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "user", path = "users")
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
}
