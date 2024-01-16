package com.example.sprintplannerx.Controller;

import com.example.sprintplannerx.Entities.Event;
import com.example.sprintplannerx.Entities.Task;
import com.example.sprintplannerx.Entities.User;
import com.example.sprintplannerx.Service.EventService;
import com.example.sprintplannerx.Service.TaskService;
import com.example.sprintplannerx.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.util.List;

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
    public String getTasks(@RequestParam(name = "status", required = false) String status, Model model) {
        List<Task> tasks;

        if (status != null && !status.isEmpty()) {

            tasks = taskService.getTasksByStatus(status);
        } else {
            tasks = taskService.getAllTasks();
        }

        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("userTasks")
    public String listTasksUser(Model model, Principal principal) {
        String username = principal.getName();
        List<Task> tasks = taskService.getTasksByUserName(username);
        model.addAttribute("tasks", tasks);
        model.addAttribute("user",username);
        List<User> allUsers = userService.findAllUsers();
        model.addAttribute("allUsers",allUsers);
        List<Event> allEvents = eventService.getAllEvents();
        model.addAttribute("allEvents",allEvents);
        return "tasks";
    }

    @GetMapping("userTasksStatus")
    public String listTasksUserByStatus(@RequestParam(name = "status", required = false) String status,Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        List<Task> tasks = taskService.getUserTasksByStatus(username, status);
        model.addAttribute("tasks", tasks);
        model.addAttribute("user",user);
        List<User> allUsers = userService.findAllUsers();
        model.addAttribute("allUsers",allUsers);
        List<Event> allEvents = eventService.getAllEvents();
        model.addAttribute("allEvents",allEvents);
        return "tasks";
    }

    @GetMapping("userFavorites")
    public String listTasksUserFavorites(Model model, Principal principal){
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        List<Task> favoriteTasks = taskService.getStarredTasks(username);
        model.addAttribute("favoriteTasks",favoriteTasks);
        model.addAttribute("user",user);
        List<User> allUsers = userService.findAllUsers();
        model.addAttribute("allUsers",allUsers);
        List<Event> allEvents = eventService.getAllEvents();
        model.addAttribute("allEvents",allEvents);

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

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

}
