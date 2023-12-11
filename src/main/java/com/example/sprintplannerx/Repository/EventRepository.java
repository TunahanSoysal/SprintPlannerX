package com.example.sprintplannerx.Repository;

import com.example.sprintplannerx.Entities.Event;
import com.example.sprintplannerx.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
