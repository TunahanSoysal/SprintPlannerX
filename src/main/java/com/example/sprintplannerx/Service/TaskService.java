package com.example.sprintplannerx.Service;

import com.example.sprintplannerx.Entities.Event;
import com.example.sprintplannerx.Entities.Task;
import com.example.sprintplannerx.Entities.User;
import com.example.sprintplannerx.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

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

}