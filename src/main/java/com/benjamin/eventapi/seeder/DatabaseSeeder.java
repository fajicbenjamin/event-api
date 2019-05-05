package com.benjamin.eventapi.seeder;

import com.benjamin.eventapi.model.User;
import com.benjamin.eventapi.model.Category;
import com.benjamin.eventapi.model.Event;
import com.benjamin.eventapi.model.Location;
import com.benjamin.eventapi.repository.UserRepository;
import com.benjamin.eventapi.repository.CategoryRepository;
import com.benjamin.eventapi.repository.EventRepository;
import com.benjamin.eventapi.repository.LocationRepository;
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

    @Autowired
    public DatabaseSeeder(UserRepository userRepository, EventRepository eventRepository,
                          CategoryRepository categoryRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedUsersTable(userRepository);
        seedCategoriesTable(categoryRepository);
        seedLocationsTable(locationRepository);
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

        Event event = new Event();
        event.setName("Testni event");
        event.setStartTime(new Date().toInstant().plusSeconds(86400));
        event.setEndTime(new Date().toInstant());
        event.setAvailablePlaces(100);
        event.setDescription("Testni event koji je odseedan na samom startu apija");
        event.setCategory(categories.get(0));
        event.setLocation(locations.get(0));
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
}
