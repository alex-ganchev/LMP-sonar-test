package digital.razgrad.LMP.controller;

import digital.razgrad.LMP.constant.AnswerType;
import digital.razgrad.LMP.constant.CourseType;
import digital.razgrad.LMP.dto.QuestionRegistrationDTO;
import digital.razgrad.LMP.entity.Answer;
import digital.razgrad.LMP.entity.Question;
import digital.razgrad.LMP.mapper.QuestionRegistrationMapper;
import digital.razgrad.LMP.repository.LectureRepository;
import digital.razgrad.LMP.repository.QuestionRepository;
import digital.razgrad.LMP.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionRegistrationMapper questionRegistrationMapper;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/add")
    private String addQuestion(Model model){
        List<Answer> answerSet = new ArrayList<>();
        QuestionRegistrationDTO questionRegistrationDTO = new QuestionRegistrationDTO();
        for (int i = 0; i <= 3; i++) {
            answerSet.add(new Answer());
        }
        questionRegistrationDTO.setAnswerSet(answerSet);
        model.addAttribute("questionRegistrationDTO", questionRegistrationDTO);
        model.addAttribute("answerTypeList", AnswerType.values());
        model.addAttribute("lectureList", lectureRepository.findAll());
        return "/question/add";
    }
    @PostMapping("/add")
    private String saveQuestion(@Valid @ModelAttribute QuestionRegistrationDTO questionRegistrationDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
       Question question = questionRegistrationMapper.toEntityQuestion(questionRegistrationDTO);
       Set<Answer> answerSet = new HashSet<>(questionRegistrationDTO.getAnswerSet());
       questionService.saveQuestionWithAnswers(question,answerSet);
       return "redirect:/question/add";
    }
}
