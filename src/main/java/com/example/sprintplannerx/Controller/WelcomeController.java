package com.example.sprintplannerx.Controller;

import com.example.sprintplannerx.Entities.User;
import com.example.sprintplannerx.Service.SecurityService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class WelcomeController {

    private final SecurityService securityService;

    public WelcomeController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/welcome")
    public String greeting(Model model) {
        model.addAttribute("authentication", securityService.getAuthentication());
        return "welcome";
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
