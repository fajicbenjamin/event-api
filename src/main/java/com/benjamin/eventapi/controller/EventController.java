package com.benjamin.eventapi.controller;

import com.benjamin.eventapi.model.Event;
import com.benjamin.eventapi.repository.EventRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {
    private EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
