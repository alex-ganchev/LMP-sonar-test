package digital.razgrad.LMP.service;

import digital.razgrad.LMP.constant.AnswerType;
import digital.razgrad.LMP.dto.QuestionRegistrationDTO;
import digital.razgrad.LMP.entity.Answer;
import digital.razgrad.LMP.entity.Question;
import digital.razgrad.LMP.hellper.EntityValidator;
import digital.razgrad.LMP.mapper.QuestionRegistrationMapper;
import digital.razgrad.LMP.repository.AnswerRepository;
import digital.razgrad.LMP.repository.LectureRepository;
import digital.razgrad.LMP.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private QuestionRegistrationMapper questionRegistrationMapper;
    @Autowired
    private EntityValidator entityValidator;


    public String addQuestion(Model model) {
        List<Answer> answerList = new ArrayList<>();
        QuestionRegistrationDTO questionRegistrationDTO = new QuestionRegistrationDTO();
        for (int i = 0; i <= 3; i++) {
            answerList.add(new Answer());
        }
        questionRegistrationDTO.setAnswerList(answerList);
        model.addAttribute("questionRegistrationDTO", questionRegistrationDTO);
        model.addAttribute("answerTypeList", AnswerType.values());
        model.addAttribute("lectureList", lectureRepository.findAll());
        return "/question/add";
    }

    public String saveQuestion(QuestionRegistrationDTO questionRegistrationDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        boolean isValidQuestionForm = validateQuestionForm(questionRegistrationDTO);
        if (bindingResult.hasErrors() || !isValidQuestionForm) {
            model.addAttribute("answerTypeList", AnswerType.values());
            model.addAttribute("lectureList", lectureRepository.findAll());
            model.addAttribute("message", isValidQuestionForm ? "" : "Има несъответствие в типа на въпроса и посечените отговори!");
            return "/question/add";
        }
        Question question = questionRegistrationMapper.toEntityQuestion(questionRegistrationDTO);
        List<Answer> answerList = questionRegistrationDTO.getAnswerList();
        redirectAttributes.addFlashAttribute("message", entityValidator.checkSaveSuccess(saveQuestionWithAnswers(question, answerList)));
        return "redirect:/question/add";
    }

    private boolean validateQuestionForm(QuestionRegistrationDTO questionRegistrationDTO) {
        int answerNumber = 0;
        int correctAnswer = 0;
        for (Answer answer : questionRegistrationDTO.getAnswerList()) {
            if (!answer.getAnswer().isEmpty()) {
                answerNumber++;
                if (answer.isCorrect()) {
                    correctAnswer++;
                }
            }
        }
        if (questionRegistrationDTO.getAnswerType().equals(AnswerType.OPEN) && answerNumber == 0) {
            return true;
        } else if (questionRegistrationDTO.getAnswerType().equals(AnswerType.ONE) && correctAnswer == 1 && answerNumber > 1) {
            return true;
        } else if (questionRegistrationDTO.getAnswerType().equals(AnswerType.MANY) && correctAnswer >= 2 && answerNumber > 2) {
            return true;
        }
        return false;
    }

    @Transactional
    private Question saveQuestionWithAnswers(Question question, List<Answer> answers) {
        Question savedQuestion = questionRepository.save(question);
        for (Answer answer : answers) {
            if (!answer.getAnswer().isEmpty()) {
                answer.setQuestion(savedQuestion);
                answerRepository.save(answer);
            }
        }
        return savedQuestion;
    }
}
