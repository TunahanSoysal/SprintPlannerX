package com.example.sprintplannerx.Service;

import com.example.sprintplannerx.Entities.Event;
import com.example.sprintplannerx.Entities.Task;
import com.example.sprintplannerx.Entities.User;
import com.example.sprintplannerx.Repository.TaskRepository;
import com.example.sprintplannerx.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> getUserTasksByStatus(String username,String status) {
        return taskRepository.findByUserAndStatus(username,status);
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
    public Task updateOneTask(Long taskId, Task newTask) {
        Optional<Task> task = taskRepository.findById(taskId);

        if (task.isPresent()){
            Task foundTask = task.get();
            foundTask.setName(newTask.getName());
            foundTask.setStatus(newTask.getStatus());
            taskRepository.save(foundTask);
            return foundTask;
        }else{
            return  null;
        }
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getStarredTasks(String username) {

        List<Task> allTasks = taskRepository.findAll();
        return allTasks.stream()
                .filter(Task::isStarred)
                .filter(task -> task.getDeveloper().getUsername().equals(username) || task.getAnalyst().getUsername().equals(username))
                .collect(Collectors.toList());
    }


    public List<Task> getTasksByUserName(String username) {

        List<Task> allTasks = taskRepository.findAll();
        return allTasks.stream()
                .filter(task -> task.getDeveloper().getUsername().equals(username) || task.getAnalyst().getUsername().equals(username))
                .collect(Collectors.toList());
    }

    public int getDoneTaskCountForUser(String username) {
        return taskRepository.getDoneTaskCountForUser(username);
    }
    public int getToDoTaskCountForUser(String username) {
        return taskRepository.getToDoTaskCountForUser(username);
    }
    public int getOverDueTaskCountForUser(String username) {
        return taskRepository.getOverdueTaskCountForUser(username);
    }

    public int getTotalTaskCountForUser(String username) {
        return getDoneTaskCountForUser(username)
                +getOverDueTaskCountForUser(username)
                 +getToDoTaskCountForUser(username);
    }

    public List<Task> getTasksByDueDateASC(String username){
        return taskRepository.findAllOrderByDueDate(username);

    }


}