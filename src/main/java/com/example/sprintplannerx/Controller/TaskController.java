package com.example.sprintplannerx.Controller;

import com.example.sprintplannerx.Entities.Event;
import com.example.sprintplannerx.Entities.Task;
import com.example.sprintplannerx.Entities.User;
import com.example.sprintplannerx.Service.TaskService;
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
        return "tasks";
    }

    @GetMapping("userTasksStatus")
    public String listTasksUserByStatus(@RequestParam(name = "status", required = false) String status,Model model, Principal principal) {
        String username = principal.getName();
        List<Task> tasks = taskService.getUserTasksByStatus(username, status);
        model.addAttribute("tasks", tasks);
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

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

}
