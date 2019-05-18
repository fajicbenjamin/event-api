package com.benjamin.eventapi.service;

import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageProperties {
    /**
     * Folder location for storing files
     */
    private String location = "src/main/resources/public/";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
