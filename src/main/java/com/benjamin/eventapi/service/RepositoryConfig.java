package com.benjamin.eventapi.service;

import com.benjamin.eventapi.model.Category;
import com.benjamin.eventapi.model.Event;
import com.benjamin.eventapi.model.Location;
import com.benjamin.eventapi.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Event.class, Category.class, Location.class, User.class);
    }
}
