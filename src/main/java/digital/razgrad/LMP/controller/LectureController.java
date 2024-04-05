package digital.razgrad.LMP.controller;

import digital.razgrad.LMP.entity.Course;
import digital.razgrad.LMP.entity.Lecture;
import digital.razgrad.LMP.repository.CourseRepository;
import digital.razgrad.LMP.repository.LectureRepository;
import digital.razgrad.LMP.repository.ModuleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/lecture")
public class LectureController {
    @Autowired
    ModuleRepository moduleRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private LectureRepository lectureRepository;

    @GetMapping("/select-course")
    private String selectCourse(Model model) {
        model.addAttribute("courseList", courseRepository.findAll());
        return "/lecture/select-course";
    }

    @PostMapping("/select-course")
    private String selectCourse(@RequestParam Long id, Model model) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            model.addAttribute("moduleList", optionalCourse.get().getModuleSet());
            model.addAttribute("lecture", new Lecture());
            return "/lecture/add";
        }
        return "redirect:/lecture/select-course";
    }

    @PostMapping("/add")
    private String saveLecture(@Valid @ModelAttribute Lecture lecture, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // model.addAttribute("lecture", lecture);
            return "/lecture/add";
        }
        lectureRepository.save(lecture);
        return "/lecture/select-course";
    }

    @GetMapping("/list")
    private String listLecture(Model model) {
        model.addAttribute("lectureList", lectureRepository.findAll());
        return ("/lecture/list");
    }
}
