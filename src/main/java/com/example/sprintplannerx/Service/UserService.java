package com.example.sprintplannerx.Service;

import com.example.sprintplannerx.Entities.Role;
import com.example.sprintplannerx.Entities.Task;
import com.example.sprintplannerx.Entities.User;
import com.example.sprintplannerx.Repository.RoleRepository;
import com.example.sprintplannerx.Repository.TaskRepository;
import com.example.sprintplannerx.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, TaskRepository taskRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    public User saveUser(User newUser) {
        return userRepository.save(newUser);
    }
    public User getOneUser(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User getUserByUsername(String username){return userRepository.findByUsername(username).orElse(null);}
    public User updateOneUser(Integer userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            User foundUser = user.get();
            foundUser.setUsername(newUser.getUsername());
            foundUser.setPassword(newUser.getPassword());
            userRepository.save(foundUser);
            return foundUser;
        }else{
            return  null;
        }
    }

    public void deleteUserById(Integer userId) {
        userRepository.deleteById(userId);
    }
    public void registerUser(String name, String username,String email, String password) throws Exception {

        if (userRepository.findByUsername(username).isEmpty()) {

            User user = new User();
            user.setUsername(username);
            user.setName(name);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));

            userRepository.save(user);
        } else {
            throw new Exception("there is already a user using that username");
        }
    }

    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found with name: " + roleName));

        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }

    public Task getOnTrackTaskByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return user.getOnTrackedTask();
    }
    public void updateOnTrackTaskByID(Integer userId, Long taskId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with this ID: " + userId));

        Task newTask = taskRepository.getTaskByID(taskId);

        user.setOnTrackedTask(newTask);
        System.out.println("new task ="+user.getOnTrackedTask());
    }
}
