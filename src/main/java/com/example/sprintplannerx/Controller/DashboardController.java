package com.example.sprintplannerx.Controller;


import com.example.sprintplannerx.Service.EventService;
import com.example.sprintplannerx.Service.SecurityService;
import com.example.sprintplannerx.Service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class DashboardController {

    private final SecurityService securityService;
    private final TaskService taskService;
    private final EventService eventService;

    public DashboardController(SecurityService securityService, TaskService taskService, EventService eventService) {
        this.securityService = securityService;
        this.taskService = taskService;
        this.eventService = eventService;
    }

    @GetMapping("/dashboard")
    public String greeting(Model model) {
        Authentication authentication = securityService.getAuthentication();

        model.addAttribute("authentication", authentication);
        model.addAttribute("starredTasks", taskService.getStarredTasks(authentication)); // currentUser: Oturum açmış kullanıcı nesnesi
        model.addAttribute("registeredEvents", eventService.getRegisteredEvents(authentication));

        return "dashboard";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

}
