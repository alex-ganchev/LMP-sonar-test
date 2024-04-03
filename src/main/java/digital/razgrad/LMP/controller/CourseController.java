package digital.razgrad.LMP.controller;

import digital.razgrad.LMP.constant.CourseStatus;
import digital.razgrad.LMP.constant.CourseType;
import digital.razgrad.LMP.entity.Course;
import digital.razgrad.LMP.repository.CourseRepository;
import digital.razgrad.LMP.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseService courseService;

    @GetMapping("/add")
    private String addCourse(Model model) {
        model.addAttribute("typeList", CourseType.values());
        model.addAttribute("statusList", CourseStatus.values());
        model.addAttribute("course", new Course());
        return "/course/add";
    }

    @PostMapping("/add")
    private String saveCourse(@Valid @ModelAttribute Course course, BindingResult bindingResult, Model model) {
        return courseService.saveCourse(course, bindingResult, model);
    }
    @GetMapping("/list")
    private String listAllCourse(Model model) {
        model.addAttribute("courseList", courseRepository.findAll());
        return "/course/list";
    }

}
