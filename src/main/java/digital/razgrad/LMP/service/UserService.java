package digital.razgrad.LMP.service;


import digital.razgrad.LMP.auth.MyUserDetails;
import digital.razgrad.LMP.constant.CourseStatus;
import digital.razgrad.LMP.constant.CourseType;
import digital.razgrad.LMP.constant.UserRole;
import digital.razgrad.LMP.dto.UserProfileDTO;
import digital.razgrad.LMP.dto.UserRegistrationDTO;
import digital.razgrad.LMP.dto.UserUpdateDTO;
import digital.razgrad.LMP.entity.Course;
import digital.razgrad.LMP.entity.Student;
import digital.razgrad.LMP.entity.Teacher;
import digital.razgrad.LMP.entity.User;
import digital.razgrad.LMP.hellper.EntityValidator;
import digital.razgrad.LMP.mapper.UserProfileMapper;
import digital.razgrad.LMP.mapper.UserRegistrationMapper;
import digital.razgrad.LMP.mapper.UserUpdateMapper;
import digital.razgrad.LMP.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserRegistrationMapper userRegistrationMapper;
    private UserUpdateMapper userUpdateMapper;
    private UserProfileMapper userProfileMapper;
    private EntityValidator entityValidator;
    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    private void setUserRegistrationMapper(UserRegistrationMapper userRegistrationMapper) {
        this.userRegistrationMapper = userRegistrationMapper;
    }
    @Autowired
    private void setUserUpdateMapper(UserUpdateMapper userUpdateMapper) {
        this.userUpdateMapper = userUpdateMapper;
    }
    @Autowired
    private void setUserProfileMapper(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }
    @Autowired
    private void setEntityValidator(EntityValidator entityValidator) {
        this.entityValidator = entityValidator;
    }

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

    public String editUser(Long id, Model model) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            model.addAttribute("roleList", UserRole.values());
            model.addAttribute("userUpdateDTO", userUpdateMapper.toDTO(optionalUser.get()));
            return "/user/edit";
        }
        return "redirect:/user/list";
    }

    public String updateUser(UserUpdateDTO userUpdateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors() || !userUpdateDTO.getPassword().equals(userUpdateDTO.getRepeatPassword())) {
            model.addAttribute("roleList", UserRole.values());
            model.addAttribute("passwordMessage", !userUpdateDTO.getPassword().equals(userUpdateDTO.getRepeatPassword()) ? "Паролите не съвпадат!" : "");
            return "user/edit";
        }
        User user = userUpdateMapper.toEntity(userUpdateDTO);
        if (user.getPassword().length() < 4) {
            user.setPassword(userRepository.findById(user.getId()).get().getPassword());
        } else {
            user.setPassword(passwordEncoder().encode(user.getPassword()));
        }
        user.setEnabled(true);
        redirectAttributes.addFlashAttribute("message", entityValidator.checkSaveSuccess(userRepository.save(user)));
        return "redirect:/user/list";
    }

    public String editProfile(MyUserDetails userDetails, Model model) {
        if (userDetails != null) {
            userDetails.getId();
            Optional<User> loggedInUser = userRepository.findById(userDetails.getId());
            if (loggedInUser.isPresent()) {
                model.addAttribute("userUpdateDTO", userUpdateMapper.toDTO(loggedInUser.get()));
                return "user/profile-edit";
            }
        }
        return "redirect:/profile";
    }

    public String updateProfile(UserUpdateDTO userUpdateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors() || !userUpdateDTO.getPassword().equals(userUpdateDTO.getRepeatPassword())) {
            model.addAttribute("passwordMessage", !userUpdateDTO.getPassword().equals(userUpdateDTO.getRepeatPassword()) ? "Паролите не съвпадат!" : "");
            model.addAttribute("userUpdateDTO", userUpdateDTO);
            return "user/profile-edit";
        }
        userUpdateDTO.setUserRole(userRepository.findById(userUpdateDTO.getId()).get().getUserRole());
        User user = userUpdateMapper.toEntity(userUpdateDTO);
        if (user.getPassword().length() < 4) {
            model.addAttribute("message", "Паролaта е твърде кратка!");
            return "user/profile-edit";
        }
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        user.setEnabled(true);
        redirectAttributes.addFlashAttribute("message", entityValidator.checkSaveSuccess(userRepository.save(user)));
        return "redirect:/profile";
    }

    public String deleteUser(Long id, RedirectAttributes redirectAttributes, Model model) {
        if (id != null) {
            try {
                userRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("message", entityValidator.checkDeleteSuccess(userRepository.existsById(id)));
            } catch (Exception SQLIntegrityConstraintViolationException) {
                redirectAttributes.addFlashAttribute("message", "Потребителя участва в релации. Не можете да изтриете потребителя!");
            }
        }
        return "redirect:/user/list";
    }
//    private boolean validateSafeDeleteUser(Long id) {
//        Optional<User> optionalUser = userRepository.findById(id);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            if (user instanceof Student && ((Student) user).getModules().isEmpty()
//                    && ((Student) user).getTestResults().isEmpty()
//                    && ((Student) user).getCourses().isEmpty()
//                    && ((Student) user).getApplicationList().isEmpty()
//                    && ((Student) user).getTestStudentAnswerList().isEmpty())
//            {
//                return true;
//            } else if (user instanceof Teacher && ((Teacher) user).getCourses().isEmpty()
//            ) {
//                return true;
//            } else{
//                return true;
//            }
//        }
//       return false;
//    }
}
