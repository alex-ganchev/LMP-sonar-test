package digital.razgrad.LMP.controller;

import digital.razgrad.LMP.entity.Lecture;
import digital.razgrad.LMP.repository.CourseRepository;
import digital.razgrad.LMP.repository.LectureRepository;
import digital.razgrad.LMP.service.LectureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/lecture")
public class LectureController {


    private CourseRepository courseRepository;

    private LectureRepository lectureRepository;

    private LectureService lectureService;

    @Autowired
    private void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    private void setLectureRepository(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @Autowired
    private void setLectureService(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping("/select-course")
    private String selectCourse(Model model) {
        model.addAttribute("courseList", courseRepository.findAll());
        return "/lecture/select-course";
    }

    @PostMapping("/select-course")
    private String selectCourse(@RequestParam Long id, RedirectAttributes redirectAttributes, Model model) {
        return lectureService.selectCourse(id, redirectAttributes, model);
    }

    @PostMapping("/add")
    private String saveLecture(@Valid @ModelAttribute Lecture lecture, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        return lectureService.saveLecture(lecture, bindingResult, redirectAttributes, model);
    }

    @GetMapping("/list")
    private String listAllLecture(Model model, Authentication authentication) {
       return lectureService.listAllLecture(model, authentication);
    }

    @GetMapping("/edit")
    private String editLecture(@RequestParam Long id, Model model) {
        return lectureService.editLecture(id, model);
    }

    @PostMapping("/edit")
    private String updateLecture(@Valid @ModelAttribute Lecture lecture, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        return lectureService.updateLecture(lecture, bindingResult, redirectAttributes, model);
    }

    @PostMapping("/delete")
    private String deleteLecture(@RequestParam Long id, RedirectAttributes redirectAttributes, Model model) {
        return lectureService.deleteLecture(id, redirectAttributes, model);
    }
}
