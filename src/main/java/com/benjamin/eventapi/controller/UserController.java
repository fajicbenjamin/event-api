package com.benjamin.eventapi.controller;

import com.benjamin.eventapi.model.User;
import com.benjamin.eventapi.repository.UserRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping(value = "/users")
    public List<User> index() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        if (newUser.getPassword() != null && !newUser.getPassword().equals("")) {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        }

        return userRepository.save(newUser);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + id));
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {


        return userRepository.findById(id)
                .map(User -> {
                    User.setUsername(newUser.getUsername());
                    User.setAdmin(newUser.isAdmin());
                    User.setEmail(newUser.getEmail());
                    if (newUser.getPassword() != null && !newUser.getPassword().equals("")) {
                        User.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
                    }

                    return userRepository.save(User);
                })
                .orElseGet(() -> {
//                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
