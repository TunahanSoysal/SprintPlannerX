package com.example.sprintplannerx.Controller;

import com.example.sprintplannerx.Entities.Event;
import com.example.sprintplannerx.Entities.Role;
import com.example.sprintplannerx.Entities.Task;
import com.example.sprintplannerx.Entities.User;
import com.example.sprintplannerx.Service.EventService;
import com.example.sprintplannerx.Service.TaskService;
import com.example.sprintplannerx.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @GetMapping()
    public String getTasks(@RequestParam(name = "status", required = false) String status, Model model, Authentication authentication) {
        List<Task> tasks;

        if (status != null && !status.isEmpty()) {

            tasks = taskService.getTasksByStatus(status);
        } else {
            tasks = taskService.getAllTasks();
        }

        model.addAttribute("tasks", tasks);
        if (authentication != null && authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            boolean isAdmin = authorities.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
            model.addAttribute("isAdmin", isAdmin);
        }
        return "tasks";
    }

    @GetMapping("userTasks")
    public String listTasksUser(Model model, Principal principal, Authentication authentication) {
        String username = principal.getName();
        List<Task> tasks = taskService.getTasksByUserName(username);
        model.addAttribute("tasks", tasks);
        model.addAttribute("userName",username);
        List<User> allUsers = userService.findAllUsers();
        model.addAttribute("allUsers",allUsers);
        List<Event> allEvents = eventService.getAllEvents();
        model.addAttribute("allEvents",allEvents);
        User user = userService.getUserByUsername(username);
        model.addAttribute("user",user);
        Set<Role> userRoles = user.getRoles();
        model.addAttribute("userRoles",userRoles);
        if (authentication != null && authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            boolean isAdmin = authorities.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
            model.addAttribute("isAdmin", isAdmin);
        }


        return "tasks";
    }

    @GetMapping("userTasksStatus")
    public String listTasksUserByStatus(@RequestParam(name = "status", required = false) String status,Model model, Principal principal, Authentication authentication) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        List<Task> tasks = taskService.getUserTasksByStatus(username, status);
        model.addAttribute("tasks", tasks);
        model.addAttribute("user",user);
        List<User> allUsers = userService.findAllUsers();
        model.addAttribute("allUsers",allUsers);
        List<Event> allEvents = eventService.getAllEvents();
        model.addAttribute("allEvents",allEvents);
        if (authentication != null && authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            boolean isAdmin = authorities.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
            model.addAttribute("isAdmin", isAdmin);
        }
        return "tasks";
    }

    @GetMapping("userFavorites")
    public String listTasksUserFavorites(Model model, Principal principal, Authentication authentication){
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        List<Task> starredTasks = taskService.getStarredTasks(username);
        model.addAttribute("starredTasks",starredTasks);
        model.addAttribute("user",user);
        List<User> allUsers = userService.findAllUsers();
        model.addAttribute("allUsers",allUsers);
        List<Event> allEvents = eventService.getAllEvents();
        model.addAttribute("allEvents",allEvents);
        if (authentication != null && authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            boolean isAdmin = authorities.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
            model.addAttribute("isAdmin", isAdmin);
        }

        return "tasks";
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);

    }

    @PostMapping
    public Task createTask(
            @RequestParam String name,
            @RequestParam String status,
            @RequestParam User developer,
            @RequestParam User analyst,
            @RequestParam Date dueDate,
            @RequestParam Integer finalSP,
            @RequestParam Event event
    ) {
        return taskService.createTask(name, status, developer, analyst, dueDate, finalSP, event);
    }

    @PutMapping(value ="/{taskId}", consumes = "application/json", produces = "application/json")
    public Task updateOneTask(@PathVariable Long taskId,
                              @RequestBody Task newTask){
        return taskService.updateOneTask(taskId,newTask);
    }

    @PutMapping("/updateTaskStatus/{taskId}")
    public Task updateTaskStatus(@PathVariable String taskId, @RequestBody String newStatus) {
        Long newID = Long.parseLong(taskId);

        return taskService.updateTaskStatus(newID, newStatus);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

}
