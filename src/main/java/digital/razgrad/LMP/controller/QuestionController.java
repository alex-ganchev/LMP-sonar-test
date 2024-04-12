package digital.razgrad.LMP.controller;

import digital.razgrad.LMP.dto.QuestionRegistrationDTO;
import digital.razgrad.LMP.repository.QuestionRepository;
import digital.razgrad.LMP.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/question")
public class QuestionController {

    private QuestionRepository questionRepository;

    private QuestionService questionService;

    @Autowired
    private void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    private void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/add")
    private String addQuestion(Model model) {
        return questionService.addQuestion(model);
    }

    @PostMapping("/add")
    private String saveQuestion(@Valid @ModelAttribute QuestionRegistrationDTO questionRegistrationDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        return questionService.saveQuestion(questionRegistrationDTO, bindingResult, redirectAttributes, model);
    }

    @GetMapping("/list")
    private String listAllQuestion(Model model) {
        model.addAttribute("questionList", questionRepository.findAll());
        return "/question/list";
    }

    @GetMapping("/edit")
    private String editQuestion(@RequestParam Long id, Model model) {
        return questionService.editQuestion(id, model);
    }

//    @PostMapping("/edit")
//    private String updateQuestion(@Valid @ModelAttribute Question question, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
//        return questionService.updateQuestion(question, bindingResult, redirectAttributes, model);
//    }
    @PostMapping("/delete")
    private String deleteQuestion(@RequestParam Long id, RedirectAttributes redirectAttributes, Model model) {
        return questionService.deleteQuestion(id, redirectAttributes, model);
    }
}
