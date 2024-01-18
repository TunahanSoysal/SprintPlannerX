package com.example.sprintplannerx.Controller;


import com.example.sprintplannerx.Entities.Event;
import com.example.sprintplannerx.Entities.Task;
import com.example.sprintplannerx.Entities.User;
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
    public String greeting(Model model) {
        Authentication authentication = securityService.getAuthentication();
        model.addAttribute("authentication", authentication);
        importModels(model, authentication);

        return "index";
    }

    @GetMapping("/dashboard2")
    public String getDashboard2(Model model) {
        Authentication authentication = securityService.getAuthentication();
        model.addAttribute("authentication", authentication);
        importModels(model, authentication);

        return "dashboard2";
    }

    private void importModels(Model model, Principal principal) {

        String username = principal.getName();

        int doneTaskCount = taskService.getDoneTaskCountForUser(username);
        model.addAttribute("doneTaskCount",doneTaskCount);

        int toDoTaskCount = taskService.getToDoTaskCountForUser(username);
        model.addAttribute("toDoTaskCount",toDoTaskCount);

        int overdueTaskCount = taskService.getOverDueTaskCountForUser(username);
        model.addAttribute("overdueTaskCount",overdueTaskCount);

        int totalTaskCount = taskService.getTotalTaskCountForUser(username);
        model.addAttribute("totalTaskCount",totalTaskCount);

        Authentication authentication = securityService.getAuthentication();
        model.addAttribute("authentication", authentication);

        List<Task> starredTasks = taskService.getStarredTasks(username);
        model.addAttribute("starredTasks", starredTasks);

        Task onTrackedTask = userService.getOnTrackTaskByUsername(username);
        model.addAttribute("onTrackedTask",onTrackedTask);

        List<Event> registeredEvents = eventService.getRegisteredEvents(authentication);
        model.addAttribute("registeredEvents", registeredEvents);

        List<Task> upcomingTasks = taskService.getTasksByDueDateASC(username);
        model.addAttribute("upcomingTasks", upcomingTasks);

        List<Task> tasks = taskService.getTasksByUserName(username);
        model.addAttribute("UserTasks", tasks);

        List<User> allUsers = userService.findAllUsers();
        model.addAttribute("allUsers",allUsers);

        List<Event> allEvents = eventService.getAllEvents();
        model.addAttribute("allEvents",allEvents);
    }

    @GetMapping("/board")
    public String getBoard() {return "board";}

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
