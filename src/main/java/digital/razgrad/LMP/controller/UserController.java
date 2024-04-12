package digital.razgrad.LMP.controller;

import digital.razgrad.LMP.auth.MyUserDetails;
import digital.razgrad.LMP.dto.UserRegistrationDTO;
import digital.razgrad.LMP.dto.UserUpdateDTO;
import digital.razgrad.LMP.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    private void setUserService(UserService userService) {
        this.userService = userService;
    }

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
        return userService.saveUser(userRegistrationDTO, bindingResult, model);
    }

    @GetMapping("/profile")
    private String showProfile(Authentication authentication, Model model) {
        return userService.showProfile(authentication, model);
    }

    @GetMapping("/user/list")
    private String listAllUsers(Model model) {
        return userService.listAllUsers(model);
    }

    @GetMapping("/user/edit")
    private String editUser(@RequestParam Long id, Model model) {
        return userService.editUser(id, model);
    }

    @PostMapping("/user/edit")
    private String updateUser(@Valid @ModelAttribute UserUpdateDTO userUpdateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        return userService.updateUser(userUpdateDTO, bindingResult, redirectAttributes, model);
    }

    @GetMapping("/profile/edit")
    private String editProfile(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        return userService.editProfile(userDetails, model);
    }

    @PostMapping("/profile/edit")
    private String updateProfile(@Valid @ModelAttribute UserUpdateDTO userUpdateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        return userService.updateProfile(userUpdateDTO, bindingResult, redirectAttributes, model);
    }

    @PostMapping("/user/delete")
    private String deleteUser(@RequestParam Long id, RedirectAttributes redirectAttributes, Model model) {
        return userService.deleteUser(id, redirectAttributes, model);
    }

    @GetMapping("/access-denied")
    private String accessDenied() {
        return "/user/access-denied";
    }
}

