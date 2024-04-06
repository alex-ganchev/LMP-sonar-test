package digital.razgrad.LMP.service;

import digital.razgrad.LMP.constant.CourseStatus;
import digital.razgrad.LMP.constant.CourseType;
import digital.razgrad.LMP.entity.Course;
import digital.razgrad.LMP.hellper.EntityValidator;
import digital.razgrad.LMP.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;


@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private EntityValidator entityValidator;

    public String saveCourse(Course course, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("typeList", CourseType.values());
            return "/course/add";
        }
        course.setStatus(CourseStatus.UPCOMING);
        model.addAttribute("message", entityValidator.checkSaveSuccess(courseRepository.save(course)));
        redirectAttributes.addFlashAttribute("message", entityValidator.checkSaveSuccess(courseRepository.save(course)));
        return "redirect:/course/add";
    }

    public String editCourse(@RequestParam Long id, Model model) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            model.addAttribute("course", optionalCourse.get());
            model.addAttribute("typeList", CourseType.values());
            model.addAttribute("statusList", CourseStatus.values());
            return "/course/edit";
        }
        return "redirect:/course/list";
    }
    public String updateCourse(Course course, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("typeList", CourseType.values());
            model.addAttribute("statusList", CourseStatus.values());
            return "/course/edit";
        }
        model.addAttribute("message", entityValidator.checkSaveSuccess(courseRepository.save(course)));
        redirectAttributes.addFlashAttribute("message", entityValidator.checkSaveSuccess(courseRepository.save(course)));
        return "redirect:/course/list";
    }

    public String deleteCourse(@RequestParam Long id, RedirectAttributes redirectAttributes, Model model) {
        if (entityValidator.checkSafeDeleteCourse(id)) {
            courseRepository.deleteById(id);
        }
        redirectAttributes.addFlashAttribute("message", entityValidator.checkDeleteSuccess(courseRepository.existsById(id)));
        return "redirect:/course/list";
    }

}
