package com.benjamin.eventapi.controller;

import com.benjamin.eventapi.model.Event;
import com.benjamin.eventapi.repository.EventRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {
    private EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping(value = "/events")
    public List<Event> index() {
        return eventRepository.findAll();
    }

    @PostMapping("/events")
    Event newEvent(@RequestBody Event newEvent) {
        return eventRepository.save(newEvent);
    }

    @GetMapping("/events/{id}")
    Event one(@PathVariable Long id) {

        return eventRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException("Event not found on :: " + id));
    }

    @PutMapping("/events/{id}")
    Event replaceEvent(@RequestBody Event newEvent, @PathVariable Long id) {

        return eventRepository.findById(id)
                .map(Event -> {
                    Event.setName(newEvent.getName());
                    return eventRepository.save(Event);
                })
                .orElseGet(() -> {
//                    newEvent.setId(id);
                    return eventRepository.save(newEvent);
                });
    }

    @DeleteMapping("/events/{id}")
    void deleteEvent(@PathVariable Long id) {
        eventRepository.deleteById(id);
    }
}
