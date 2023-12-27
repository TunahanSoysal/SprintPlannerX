package com.example.sprintplannerx.Controller;


import com.example.sprintplannerx.Entities.Event;
import com.example.sprintplannerx.Entities.Task;
import com.example.sprintplannerx.Service.EventService;
import com.example.sprintplannerx.Service.SecurityService;
import com.example.sprintplannerx.Service.TaskService;
import com.example.sprintplannerx.Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping()
public class DashboardController {

    private final SecurityService securityService;
    private final TaskService taskService;
    private final EventService eventService;
    private final UserService userService;

    public DashboardController(SecurityService securityService, TaskService taskService, EventService eventService, UserService userService) {
        this.securityService = securityService;
        this.taskService = taskService;
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String greeting(Model model, Principal principal) {
        Authentication authentication = securityService.getAuthentication();

        String username = principal.getName();


        model.addAttribute("authentication", authentication);

        //model.addAttribute("logo", "resources/static/images/logo.png");


        List<Task> starredTasks = taskService.getStarredTasks(username);
        model.addAttribute("starredTasks", starredTasks);

        Task onTrackedTask = userService.getOnTrackTaskByUsername(username);
        model.addAttribute("onTrackedTask",onTrackedTask);


        List<Event> registeredEvents = eventService.getRegisteredEvents(authentication);
        model.addAttribute("registeredEvents", registeredEvents);

        return "dashboard";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/todo")
    public String todo() {
        return "todo";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

}
