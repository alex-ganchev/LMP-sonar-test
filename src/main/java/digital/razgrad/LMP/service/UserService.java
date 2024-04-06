package digital.razgrad.LMP.service;


import digital.razgrad.LMP.auth.MyUserDetails;
import digital.razgrad.LMP.constant.UserRole;
import digital.razgrad.LMP.dto.UserProfileDTO;
import digital.razgrad.LMP.dto.UserRegistrationDTO;
import digital.razgrad.LMP.entity.User;
import digital.razgrad.LMP.mapper.UserProfileMapper;
import digital.razgrad.LMP.mapper.UserRegistrationMapper;
import digital.razgrad.LMP.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRegistrationMapper userRegistrationMapper;
    @Autowired
    UserProfileMapper userProfileMapper;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String saveUser(UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult, Model model) {
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

    public String showProfile(Authentication authentication, Model model) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        if (optionalUser.isPresent()) {
            model.addAttribute("loggedUser", userProfileMapper.toDto(optionalUser.get()));
            return "/user/profile";
        }
        return "redirect:/";
    }

    public String listAllUsers(Model model) {
        Iterable<User> userIterable = userRepository.findAll();
        List<UserProfileDTO> UserProfileDTOList = new ArrayList<>();
        for (User user : userIterable) {
            UserProfileDTOList.add(userProfileMapper.toDto(user));
        }
        model.addAttribute("userList", UserProfileDTOList);
        return "/user/list";
    }
}
