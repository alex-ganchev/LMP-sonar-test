package digital.razgrad.LMP.service;

import digital.razgrad.LMP.entity.Test;
import digital.razgrad.LMP.hellper.EntityValidator;
import digital.razgrad.LMP.repository.LectureRepository;
import digital.razgrad.LMP.repository.QuestionRepository;
import digital.razgrad.LMP.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class TestService {
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    EntityValidator entityValidator;
    @Autowired
    TestRepository testRepository;
    @Autowired
    QuestionRepository questionRepository;
    public String saveTest(Test test, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        boolean isEnoughQuestionsAvailable = validateEnoughQuestionsAvailable(test);
        if (bindingResult.hasErrors() || !isEnoughQuestionsAvailable) {
            model.addAttribute("lectureList",lectureRepository.findAll());
            model.addAttribute("message", isEnoughQuestionsAvailable ? "" : "Зададеният брои въпроси е повече от наличните за тази лекция!");
            return ("test/add");
        }
        redirectAttributes.addFlashAttribute("message", entityValidator.checkSaveSuccess(testRepository.save(test)));
        return "redirect:/question/add";
    }
    private boolean validateEnoughQuestionsAvailable(Test test){
        if (test.getQuestionsNumber() <= questionRepository.findByLecture(test.getLecture()).size()){
            return true;
        }
        return false;
    }
}
