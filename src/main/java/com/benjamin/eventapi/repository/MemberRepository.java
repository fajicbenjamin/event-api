package com.benjamin.eventapi.repository;

import com.benjamin.eventapi.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "member", path = "members")
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
}
