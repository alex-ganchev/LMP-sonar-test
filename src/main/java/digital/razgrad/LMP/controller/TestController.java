package digital.razgrad.LMP.controller;

import digital.razgrad.LMP.entity.Test;
import digital.razgrad.LMP.entity.TestResult;
import digital.razgrad.LMP.repository.LectureRepository;
import digital.razgrad.LMP.repository.TestRepository;
import digital.razgrad.LMP.repository.TestResultRepository;
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

    private LectureRepository lectureRepository;

    private TestRepository testRepository;

    private TestService testService;

    private TestResultRepository testResultRepository;

    @Autowired
    private void setLectureRepository(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @Autowired
    private void setTestRepository(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Autowired
    private void setTestService(TestService testService) {
        this.testService = testService;
    }

    @Autowired
    private void setTestResultRepository(TestResultRepository testResultRepository) {
        this.testResultRepository = testResultRepository;
    }

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

    @PostMapping("/finish")
    private String finishTest(@Valid @ModelAttribute TestResult testResult, Authentication authentication, Model model) {
        return testService.finishTest(testResult, authentication, model);
    }

    @GetMapping("/result")
    private String viewAllTestResult(Model model) {
        model.addAttribute(testResultRepository.findAll());
        return "/test/result-list";
    }

    @GetMapping("/check")
    private String checkTestResult(@RequestParam Long id, Model model) {
        return testService.checkTestResult(id, model);
    }

    @PostMapping("/check")
    private String updateTestResult(@ModelAttribute TestResult testResult, RedirectAttributes redirectAttributes, Model model) {
        return testService.updateTestResult(testResult, redirectAttributes, model);
    }
}
