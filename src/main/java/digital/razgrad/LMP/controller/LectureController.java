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

@Controller
@RequestMapping("/lecture")
public class LectureController {
    @Autowired
    ModuleRepository moduleRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private LectureRepository lectureRepository;

    //    @GetMapping("/add")
//    private String addLecture(Model model){
//        model.addAttribute("moduleList", moduleRepository.findAll());
//        model.addAttribute("lecture", new Lecture());
//        return "/lecture/add";
//    }
    @GetMapping("/select")
    private String selectCourse(Model model) {
        model.addAttribute("courseList", courseRepository.findAll());
        //model.addAttribute("lecture", new Lecture());
        return "/lecture/select";
    }
//    @PostMapping("/select")
//    private String selectCourse(Long courseId, Model model) {
//            model.addAttribute("moduleList", courseRepository.findById(courseId).get().getModuleSet());
//            model.addAttribute("lecture", new Lecture());
//            return "/lecture/add";
//    }
    @GetMapping("/add")
    private String addLecture(@RequestParam Long courseId, Model model) {
        model.addAttribute("moduleList", courseRepository.findById(courseId).get().getModuleSet());
        model.addAttribute("lecture", new Lecture());
        return "/lecture/add";
    }

    @PostMapping("/add")
    private String saveLecture(@Valid @ModelAttribute Lecture lecture, @RequestParam Long courseId, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("moduleList", courseRepository.findById(courseId).get().getModuleSet());
            //model.addAttribute("lecture", new Lecture());
            return "/lecture/add";
        }
        lectureRepository.save(lecture);
        return "redirect:/lecture/add";
    }
}
