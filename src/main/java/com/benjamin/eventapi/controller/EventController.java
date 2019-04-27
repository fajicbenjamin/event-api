package com.benjamin.eventapi.controller;

import com.benjamin.eventapi.model.Event;
import com.benjamin.eventapi.repository.EventRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {
    private EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
//        this.accountRepository = accountRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

//    @PostMapping("/events")
//    public void storeEvent(@RequestBody Event event) {
//        eventRepository.save(event);
//    }

//    @PostMapping("/register")
//    public void register(@RequestBody Account user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        accountRepository.save(user);
//    }

//    @RequestMapping("/greeting")
//    public Event greeting(@RequestParam(value="name", defaultValue="World") String name) {
//        return new Event(1, "Benjamin");
////        return new Event(counter.incrementAndGet(),
////                String.format(template, name));
//    }
}
