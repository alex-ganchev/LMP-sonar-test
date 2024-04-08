package digital.razgrad.LMP.service;

import digital.razgrad.LMP.entity.Module;
import digital.razgrad.LMP.entity.Test;
import digital.razgrad.LMP.hellper.EntityValidator;
import digital.razgrad.LMP.repository.LectureRepository;
import digital.razgrad.LMP.repository.QuestionRepository;
import digital.razgrad.LMP.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

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
            model.addAttribute("lectureList", lectureRepository.findAll());
            model.addAttribute("message", isEnoughQuestionsAvailable ? "" : "Зададеният брои въпроси е повече от наличните за тази лекция!");
            return ("test/add");
        }
        redirectAttributes.addFlashAttribute("message", entityValidator.checkSaveSuccess(testRepository.save(test)));
        return "redirect:/question/add";
    }
    public String editTest(Long id, Model model) {
        Optional<Test> optionalTest = testRepository.findById(id);
        if (optionalTest.isPresent()) {
            model.addAttribute("lectureList", lectureRepository.findAll());
            model.addAttribute("test", optionalTest.get());
            return "/test/edit";
        }
        return "redirect:/test/list";
    }

    public String updateTest(Test test, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        boolean isEnoughQuestionsAvailable = validateEnoughQuestionsAvailable(test);
        if (bindingResult.hasErrors() || !isEnoughQuestionsAvailable) {
            model.addAttribute("lectureList", lectureRepository.findAll());
            model.addAttribute("message", isEnoughQuestionsAvailable ? "" : "Зададеният брои въпроси е повече от наличните за тази лекция!");
            return "/test/edit";
        }
        redirectAttributes.addFlashAttribute("message", entityValidator.checkSaveSuccess(testRepository.save(test)));
        return "redirect:/test/list";
    }

    public String deleteTest(Long id, RedirectAttributes redirectAttributes, Model model) {
        if (id != null) {
            try {
                testRepository.deleteById(id);
            } catch (Exception SQLIntegrityConstraintViolationException) {
                System.out.println("Error: Не можете да изтриете теста!");
            }
            redirectAttributes.addFlashAttribute("message", entityValidator.checkDeleteSuccess(testRepository.existsById(id)));
        }
        return "redirect:/test/list";
    }

    private boolean validateEnoughQuestionsAvailable(Test test) {
        if (test.getQuestionsNumber() <= questionRepository.findByLecture(test.getLecture()).size()) {
            return true;
        }
        return false;
    }
}
