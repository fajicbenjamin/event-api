package com.benjamin.eventapi.seeder;

import com.benjamin.eventapi.model.*;
import com.benjamin.eventapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DatabaseSeeder {
    private UserRepository userRepository;
    private EventRepository eventRepository;
    private CategoryRepository categoryRepository;
    private LocationRepository locationRepository;
    private MemberRepository memberRepository;

    @Autowired
    public DatabaseSeeder(UserRepository userRepository, EventRepository eventRepository,
                          CategoryRepository categoryRepository, LocationRepository locationRepository,
                          MemberRepository memberRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
        this.memberRepository = memberRepository;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedUsersTable(userRepository);
        seedCategoriesTable(categoryRepository);
        seedLocationsTable(locationRepository);
        seedMembersTable(memberRepository);
        seedEventsTable(eventRepository);
    }

    private void seedUsersTable(UserRepository userRepository) {
        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@admin.com");
        user.setAdmin(true);
        user.setPassword(new BCryptPasswordEncoder().encode("secret"));
        userRepository.save(user);
    }

    private void seedEventsTable(EventRepository eventRepository) {
        List<Category> categories = categoryRepository.findAll();
        List<Location> locations = locationRepository.findAll();
        List<Member> members = memberRepository.findAll();

        Event event = new Event();
        event.setName("Testni event");
        event.setStartTime(new Date().toInstant().plusSeconds(86400));
        event.setEndTime(new Date().toInstant());
        event.setAvailablePlaces(100);
        event.setDescription("Testni event koji je odseedan na samom startu apija");
        event.setRegistration(true);
        event.setCategory(categories.get(0));
        event.setLocation(locations.get(0));
        event.getMemberList().add(members.get(0));
        eventRepository.save(event);
    }

    private void seedCategoriesTable(CategoryRepository categoryRepository) {
        Category category = new Category("Testna");
        categoryRepository.save(category);
    }

    private void seedLocationsTable(LocationRepository locationRepository) {
        Location location = new Location();
        location.setName("Zetra");
        location.setAddress("Alipašina");
        location.setCity("Sarajevo");
        location.setCountry("Bosnia and Herzegovina");
        location.setLatitude("43.8718421");
        location.setLongitude("18.4094958,15");
        locationRepository.save(location);
    }

    private void seedMembersTable(MemberRepository memberRepository) {
        Member member = new Member();
        member.setName("John Doe");
        member.setEmail("johndoe@email.com");
        member.setPassword(new BCryptPasswordEncoder().encode("secret"));
        memberRepository.save(member);
    }
}
