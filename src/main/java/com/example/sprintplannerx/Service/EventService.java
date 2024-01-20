package com.example.sprintplannerx.Service;

import com.example.sprintplannerx.Entities.Event;
import com.example.sprintplannerx.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public Event createEvent(String eventName, Date startDate, Date endDate) {
        Event event = new Event();
        event.setEventName(eventName);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> getRegisteredEvents(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        List<Event> allEvents = eventRepository.findAll();
        return allEvents.stream()
                .filter(event -> event
                        .getTasks()
                        .stream()
                        .anyMatch(task -> task
                                .getDeveloper()
                                .getUsername()
                                .equals(username)
                                ||task
                                .getAnalyst()
                                .getUsername()
                                .equals(username)
                        )
                )
                .collect(Collectors.toList());
    }


}
