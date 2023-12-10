package com.example.sprintplannerx.Controller;

import com.example.sprintplannerx.Entities.User;
import com.example.sprintplannerx.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String register(Model model) {
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping
    public String registerUser(@RequestParam String name, @RequestParam String username,@RequestParam String email, @RequestParam String password) throws Exception {

        userService.registerUser(name, username, email, password);
        // Kullanıcıyı kaydettikten sonra, yönlendirilecek sayfayı belirleyin (örneğin, login sayfasına yönlendirebilirsiniz)
        return "redirect:/login";
    }
}