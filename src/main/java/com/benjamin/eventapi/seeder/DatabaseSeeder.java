package com.benjamin.eventapi.seeder;

import com.benjamin.eventapi.model.Account;
import com.benjamin.eventapi.model.Category;
import com.benjamin.eventapi.model.Event;
import com.benjamin.eventapi.repository.AccountRepository;
import com.benjamin.eventapi.repository.CategoryRepository;
import com.benjamin.eventapi.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DatabaseSeeder {
    private AccountRepository accountRepository;
    private EventRepository eventRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public DatabaseSeeder(AccountRepository accountRepository, EventRepository eventRepository, CategoryRepository categoryRepository) {
        this.accountRepository = accountRepository;
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedAccountsTable(accountRepository);
        seedCategoriesTable(categoryRepository);
        seedEventsTable(eventRepository);
    }

    private void seedAccountsTable(AccountRepository accountRepository) {
        Account account = new Account();
        account.setUsername("admin");
        account.setEmail("admin@admin.com");
        account.setAdmin(true);
        account.setPassword(new BCryptPasswordEncoder().encode("secret"));
        accountRepository.save(account);
    }

    private void seedEventsTable(EventRepository eventRepository) {
        List<Category> categories = categoryRepository.findAll();

        Event event = new Event();
        event.setName("Testni event");
        event.setStartTime(new Date().toInstant());
        event.setEndTime(new Date().toInstant());
        event.setAvailablePlaces(100);
        event.setDescription("Testni event koji je odseedan na samom startu apija");
        event.setCategory(categories.get(0));
        eventRepository.save(event);
    }

    private void seedCategoriesTable(CategoryRepository categoryRepository) {
        Category category = new Category("Testna");
        categoryRepository.save(category);
    }
}
