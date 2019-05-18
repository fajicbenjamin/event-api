package com.benjamin.eventapi.repository;

import com.benjamin.eventapi.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.Instant;
import java.util.List;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "event", path = "events")
public interface EventRepository extends JpaRepository<Event, Long> {
//    List<Event> findByName(@Param("name") String name);
//    Event findByName(String name);

    @Query("select e from Event e where e.startTime >= :instant")
    List<Event> findAllWithStartTimeAfter(Instant instant);
}
