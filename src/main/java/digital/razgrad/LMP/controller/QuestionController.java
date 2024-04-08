package digital.razgrad.LMP.controller;

import digital.razgrad.LMP.dto.QuestionRegistrationDTO;
import digital.razgrad.LMP.service.QuestionService;
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
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/add")
    private String addQuestion(Model model) {
        return questionService.addQuestion(model);
    }

    @PostMapping("/add")
    private String saveQuestion(@Valid @ModelAttribute QuestionRegistrationDTO questionRegistrationDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        return questionService.saveQuestion(questionRegistrationDTO, bindingResult, redirectAttributes, model);
    }
}
