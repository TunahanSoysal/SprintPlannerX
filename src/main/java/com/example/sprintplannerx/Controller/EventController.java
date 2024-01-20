package com.example.sprintplannerx.Controller;

import com.example.sprintplannerx.Entities.Event;
import com.example.sprintplannerx.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public String getEventById(@PathVariable Long id, Model model, Principal principal) {
        String username = principal.getName();
        Event event = eventService.getEventById(id);
        model.addAttribute("user",username);
        model.addAttribute("event",event);
        return "eventView";
    }

    @PostMapping
    public Event createEvent(@RequestParam String eventName,
                             @RequestParam Date startDate,
                             @RequestParam Date endDate) {
        return eventService.createEvent(eventName, startDate, endDate);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

}
