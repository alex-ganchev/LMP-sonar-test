package digital.razgrad.LMP.controller;

import digital.razgrad.LMP.entity.Test;
import digital.razgrad.LMP.repository.LectureRepository;
import digital.razgrad.LMP.repository.TestRepository;
import digital.razgrad.LMP.service.TestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    TestRepository testRepository;
    @Autowired
    TestService testService;
    @GetMapping("/add")
    private String addTest(Model model){
        model.addAttribute("test", new Test());
        model.addAttribute("lectureList",lectureRepository.findAll());
        return ("test/add");
    }
    @PostMapping("/add")
    private String saveTest(@Valid @ModelAttribute Test test, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        return testService.saveTest(test, bindingResult, redirectAttributes, model);
    }
    @GetMapping("/list")
    private String listAllTest(Model model) {
        model.addAttribute("testList", testRepository.findAll());
        return "/test/list";
    }
}
