package digital.razgrad.LMP.controller;

import digital.razgrad.LMP.dto.UserRegistrationDTO;
import digital.razgrad.LMP.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    private String login() {
        return "/user/login";
    }

    @GetMapping("/registration")
    private String addUser(Model model) {
        model.addAttribute("userRegistrationDTO", new UserRegistrationDTO());
        return "user/registration";
    }

    @PostMapping("/registration")
    private String addUser(@Valid @ModelAttribute UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult, Model model) {
        return userService.addUser(userRegistrationDTO, bindingResult, model);
    }

    @GetMapping("/profile")
    private String showProfile() {
        return "/user/profile";
    }

    @GetMapping("/access-denied")
    private String accessDenied() {
        return "/user/access-denied";
    }
}

