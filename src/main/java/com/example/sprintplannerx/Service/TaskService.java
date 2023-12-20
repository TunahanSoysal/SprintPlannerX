package com.example.sprintplannerx.Service;

import com.example.sprintplannerx.Entities.Event;
import com.example.sprintplannerx.Entities.Task;
import com.example.sprintplannerx.Entities.User;
import com.example.sprintplannerx.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task createTask(String name, String status, User developer, User analyst, Date dueDate, Integer finalSP, Event event) {
        Task task = new Task();
        task.setName(name);
        task.setStatus(status);
        task.setDeveloper(developer);
        task.setAnalyst(analyst);
        task.setDueDate(dueDate);
        task.setFinalSP(finalSP);
        task.setEvent(event);
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getStarredTasks(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        List<Task> allTasks = taskRepository.findAll();
        return allTasks.stream()
                .filter(Task::isStarred)
                .filter(task -> task.getDeveloper().getUsername().equals(username) || task.getAnalyst().getUsername().equals(username))
                .collect(Collectors.toList());
    }

}