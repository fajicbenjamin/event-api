package com.benjamin.eventapi.controller;

import com.benjamin.eventapi.model.Category;
import com.benjamin.eventapi.model.Event;
import com.benjamin.eventapi.model.Location;
import com.benjamin.eventapi.model.Member;
import com.benjamin.eventapi.repository.CategoryRepository;
import com.benjamin.eventapi.repository.EventRepository;
import com.benjamin.eventapi.repository.LocationRepository;
import com.benjamin.eventapi.repository.MemberRepository;
import com.benjamin.eventapi.service.StorageProperties;
import com.benjamin.eventapi.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.Instant;
import java.util.*;

@RestController
public class EventController {
    private EventRepository eventRepository;
    private MemberRepository memberRepository;
    private CategoryRepository categoryRepository;
    private LocationRepository locationRepository;

    @Autowired
    StorageProperties storageProperties;

    @Autowired
    StorageService storageService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public EventController(EventRepository eventRepository, MemberRepository memberRepository, StorageService storageService, CategoryRepository categoryRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.memberRepository = memberRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
    }

    @GetMapping(value = "/events")
    public List<Event> index() {
        return eventRepository.findAll();
    }

    @RequestMapping(value = "/events", method = RequestMethod.POST)
    Event newEvent(Event newEvent, MultipartFile file, long category_id, long location_id) {
        Event event = eventRepository.save(newEvent);

        Category category = categoryRepository.findById(category_id)
                .orElseThrow(() -> new  ResourceNotFoundException("Category not found on :: " + category_id));

        Location location = locationRepository.findById(location_id)
                .orElseThrow(() -> new  ResourceNotFoundException("Location not found on :: " + location_id));

        event.setCategory(category);
        event.setLocation(location);

        this.uploadImage(event, file);

        return event;
    }

    @GetMapping("/events/{id}")
    Event one(@PathVariable Long id) {

        return eventRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException("Event not found on :: " + id));
    }

    @RequestMapping(value = "/events/{id}", method = RequestMethod.PUT)
    Event replaceEvent(Event newEvent, MultipartFile file, long category_id, long location_id, @PathVariable Long id) {

        return eventRepository.findById(id)
                .map(Event -> {
                    Event.setName(newEvent.getName());
                    Event.setDescription(newEvent.getDescription());
                    Event.setStartTime(newEvent.getStartTime());
                    Event.setEndTime(newEvent.getEndTime());
                    Event.setAvailablePlaces(newEvent.getAvailablePlaces());
                    Event.setRegistration(newEvent.isRegistration());
                    Event.setRepeating(newEvent.isRepeating());

                    Category category = categoryRepository.findById(category_id)
                            .orElseThrow(() -> new  ResourceNotFoundException("Category not found on :: " + category_id));

                    Location location = locationRepository.findById(location_id)
                            .orElseThrow(() -> new  ResourceNotFoundException("Location not found on :: " + location_id));

                    Event.setCategory(category);
                    Event.setLocation(location);

                    this.uploadImage(Event, file);

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

    @GetMapping("/events/overview")
    Map dashboardOverview() {
        List<Event> events = eventRepository.findAllWithStartTimeAfter(Instant.now());

        Map map = new HashMap<String, String>();
        map.put("incomingEvents", events.size());
        map.put("nextEvent", events.get(0));

        return Collections.synchronizedMap(map);
    }

    @PostMapping("/events/{id}/add-guest")
    void makeReservation(String token, @PathVariable Long id) {
        long memberId = Long.parseLong(token.substring(0, token.indexOf("-")));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new  ResourceNotFoundException("Member not found on :: " + memberId));

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException("Event not found on :: " + id));


        if (!bCryptPasswordEncoder.matches(member.getEmail(), token.substring(token.lastIndexOf("-") + 1))) {
            throw new IllegalStateException("Token is not matching");
        }

        if (event.getAvailablePlaces() > 0) {
            event.getMemberList().add(member);
            event.setAvailablePlaces(event.getAvailablePlaces() - 1);
            eventRepository.save(event);
        }
    }

    @GetMapping("/events/{id}/remove-guest/{guest_id}")
    void removeGuest(@PathVariable Long id, @PathVariable Long guest_id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException("Event not found on :: " + id));

        Member member = memberRepository.findById(guest_id)
                .orElseThrow(() -> new  ResourceNotFoundException("Member not found on :: " + guest_id));

        if (event.getMemberList().remove(member)) {
            event.setAvailablePlaces(event.getAvailablePlaces() + 1);
        }
        eventRepository.save(event);
    }

    private void uploadImage(Event event, MultipartFile file) {
        if (file != null) {
            String eventImagePath = "images/events/" + event.getId() + "/" + file.getOriginalFilename();
            File eventImage = new File(storageProperties.getLocation() + "/" + eventImagePath);
            if (!eventImage.exists()) {
                eventImage.mkdirs();
            }
            String filename = StringUtils.cleanPath(eventImagePath);
            storageService.store(file, filename);

            event.setImagePath(eventImagePath);
            eventRepository.save(event);
        }
    }
}
