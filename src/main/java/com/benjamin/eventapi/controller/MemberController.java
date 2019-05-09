package com.benjamin.eventapi.controller;

import com.benjamin.eventapi.model.Member;
import com.benjamin.eventapi.repository.MemberRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {
    private MemberRepository memberRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberController(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping(value = "/members")
    public List<Member> index() {
        return memberRepository.findAll();
    }

    @PostMapping("/members")
    Member newMember(@RequestBody Member newMember) {
        if (newMember.getPassword() != null && !newMember.getPassword().equals("")) {
            newMember.setPassword(bCryptPasswordEncoder.encode(newMember.getPassword()));
        }

        return memberRepository.save(newMember);
    }

    @GetMapping("/members/{id}")
    Member one(@PathVariable Long id) {

        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found on :: " + id));
    }

    @PutMapping("/members/{id}")
    Member replaceMember(@RequestBody Member newMember, @PathVariable Long id) {


        return memberRepository.findById(id)
                .map(Member -> {
                    Member.setName(newMember.getName());
                    Member.setEmail(newMember.getEmail());
                    if (newMember.getPassword() != null && !newMember.getPassword().equals("")) {
                        Member.setPassword(bCryptPasswordEncoder.encode(newMember.getPassword()));
                    }

                    return memberRepository.save(Member);
                })
                .orElseGet(() -> {
//                    newMember.setId(id);
                    return memberRepository.save(newMember);
                });
    }

    @DeleteMapping("/members/{id}")
    void deleteMember(@PathVariable Long id) {
        memberRepository.deleteById(id);
    }
}
