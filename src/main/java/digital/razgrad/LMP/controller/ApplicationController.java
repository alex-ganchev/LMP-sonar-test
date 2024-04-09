package digital.razgrad.LMP.controller;

import digital.razgrad.LMP.auth.MyUserDetails;
import digital.razgrad.LMP.entity.Application;
import digital.razgrad.LMP.entity.Course;
import digital.razgrad.LMP.entity.Student;
import digital.razgrad.LMP.entity.User;
import digital.razgrad.LMP.hellper.EntityValidator;
import digital.razgrad.LMP.repository.ApplicationRepository;
import digital.razgrad.LMP.repository.CourseRepository;
import digital.razgrad.LMP.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityValidator entityValidator;

    @PostMapping
    private String addApplication(@RequestParam Long id, Authentication authentication, RedirectAttributes redirectAttributes, Model model) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Optional<User> optionalUser = userRepository.findById(userDetails.getId());
            if (optionalUser.isPresent()) {
                Application application = new Application((Student) optionalUser.get(), optionalCourse.get());
                try {
                    applicationRepository.save(application);
                    redirectAttributes.addFlashAttribute("message", "Кандидатсвахте успешно!");
                } catch (Exception SQLIntegrityConstraintViolationException) {
                    redirectAttributes.addFlashAttribute("message", "Вече сте кандитаствали за този курс!");
                }
            }
        }
        return "redirect:/course/list";
    }

    @GetMapping("/list")
    private String listAllUnProvedApplications(Model model) {
        Iterable<Application> applications = applicationRepository.findAll();
        List<Application> applicationsList = new ArrayList<>();
        for (Application application : applications) {
            if (!application.isApproved()) {
                applicationsList.add(application);
            }
        }
        model.addAttribute("applicationList", applicationsList);
        return "/application/list";
    }

    @PostMapping("/approve")
    private String applicationApprove(@RequestParam Long id, RedirectAttributes redirectAttributes, Model model) {
        Optional<Application> optionalApplication = applicationRepository.findById(id);
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            application.setApproved(true);
            applicationRepository.save(application);
            redirectAttributes.addFlashAttribute("message", "Успешно одобрихте кандидатурата!");
        }
        return "redirect:/application/list";
    }
}
