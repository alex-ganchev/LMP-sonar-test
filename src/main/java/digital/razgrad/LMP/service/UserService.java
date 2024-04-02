package digital.razgrad.LMP.service;


import digital.razgrad.LMP.constant.UserRole;
import digital.razgrad.LMP.dto.UserRegistrationDTO;
import digital.razgrad.LMP.entity.User;
import digital.razgrad.LMP.mapper.UserRegistrationMapper;
import digital.razgrad.LMP.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRegistrationMapper userRegistrationMapper;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String addUser(UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || !userRegistrationDTO.getPassword().equals(userRegistrationDTO.getRepeatPassword())) {
            model.addAttribute("passwordMessage", !userRegistrationDTO.getPassword().equals(userRegistrationDTO.getRepeatPassword()) ? "Паролите не съвпадат!" : "");
            return "user/registration";
        }
        User user = userRegistrationMapper.toEntity(userRegistrationDTO);
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        user.setEnabled(true);
        user.setUserRole(UserRole.ROLE_STUDENT);
        userRepository.save(user);
        return "redirect:/login";
    }
    public String showProfile(Long userId, Model model) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            model.addAttribute("loggedUser",optionalUser.get());
            return "/user/profile";
        }
        return "redirect:/";
    }
}
