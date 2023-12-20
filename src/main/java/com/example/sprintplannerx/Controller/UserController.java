package com.example.sprintplannerx.Controller;

import com.example.sprintplannerx.Entities.User;
import com.example.sprintplannerx.Service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }

//    @PostMapping
//    public User createUser(@RequestBody User newUser){
//        return userService.saveUser(newUser);
//    }

    @GetMapping("/{userId}")
    public User getOneUser(@PathVariable Integer userId){
        return userService.getOneUser(userId);
    }

    @PutMapping(value ="/{userId}", consumes = "application/json", produces = "application/json")
    public User updateOneUser(@PathVariable Integer userId,
                              @RequestBody User newUser){
        return userService.updateOneUser(userId,newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Integer userId){
        userService.deleteUserById(userId);
    }
}
