package digital.razgrad.LMP.service;

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
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityValidator entityValidator;
    public String addApplication(Long id, Authentication authentication, RedirectAttributes redirectAttributes, Model model) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Optional<User> optionalUser = userRepository.findById(userDetails.getId());
            if (optionalUser.isPresent()) {
                Application application = new Application((Student) optionalUser.get(), optionalCourse.get());
                try {
                    applicationRepository.save(application);
                    redirectAttributes.addFlashAttribute("message", "Кандидатствахте успешно!");
                } catch (Exception SQLIntegrityConstraintViolationException) {
                    redirectAttributes.addFlashAttribute("message", "Вече сте кандитатствали за този курс!");
                }
            }
        }
        return "redirect:/course/list";
    }
    public String listAllUnProvedApplications(Model model) {
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
    public String applicationApprove(Long id, RedirectAttributes redirectAttributes, Model model) {
        Optional<Application> optionalApplication = applicationRepository.findById(id);
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            Optional<Course> optionalCourse = courseRepository.findById(application.getCourse().getId());
            if (optionalCourse.isPresent()) {
                Optional<User> optionalUser = userRepository.findById(application.getStudent().getId());
                if (optionalUser.isPresent()) {
                    Course course = optionalCourse.get();
                    course.getStudentSet().add((Student) optionalUser.get());
                    application.setApproved(true);
                    applicationRepository.save(application);
                    courseRepository.save(course);
                    redirectAttributes.addFlashAttribute("message", "Успешно одобрихте кандидатурата!");
                }

            }
        }
        return "redirect:/application/list";
    }
}
