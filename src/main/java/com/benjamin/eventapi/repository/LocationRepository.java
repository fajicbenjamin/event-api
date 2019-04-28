package com.benjamin.eventapi.repository;

import com.benjamin.eventapi.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "location", path = "locations")
public interface LocationRepository extends JpaRepository<Location, Long> {
}
