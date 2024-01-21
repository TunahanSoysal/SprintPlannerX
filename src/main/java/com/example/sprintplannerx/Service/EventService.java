package com.example.sprintplannerx.Service;

import com.example.sprintplannerx.Entities.Event;

import com.example.sprintplannerx.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;


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


    public List<Event> getRegisteredEvents(String username) {
        return eventRepository.getRegisteredByUsername(username);
    }


}
