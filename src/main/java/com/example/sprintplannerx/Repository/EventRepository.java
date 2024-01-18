package com.example.sprintplannerx.Repository;

import com.example.sprintplannerx.Entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
        Event findEventByEventName(String eventName);
}
