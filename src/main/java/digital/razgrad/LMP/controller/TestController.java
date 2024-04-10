package digital.razgrad.LMP.controller;

import digital.razgrad.LMP.entity.Test;
import digital.razgrad.LMP.repository.LectureRepository;
import digital.razgrad.LMP.repository.TestRepository;
import digital.razgrad.LMP.service.TestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestService testService;

    @GetMapping("/add")
    private String addTest(Model model) {
        model.addAttribute("test", new Test());
        model.addAttribute("lectureList", lectureRepository.findAll());
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

    @GetMapping("/edit")
    private String editTest(@RequestParam Long id, Model model) {
        return testService.editTest(id, model);
    }

    @PostMapping("/edit")
    private String updateTest(@Valid @ModelAttribute Test test, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        return testService.updateTest(test, bindingResult, redirectAttributes, model);
    }

    @PostMapping("/delete")
    private String deleteTest(@RequestParam Long id, RedirectAttributes redirectAttributes, Model model) {
        return testService.deleteTest(id, redirectAttributes, model);
    }

    @GetMapping("/start")
    private String startTest(@RequestParam Long id, Model model) {
        return testService.startTest(id, model);
    }
}
