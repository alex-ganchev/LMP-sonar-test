package digital.razgrad.LMP.service;

import digital.razgrad.LMP.auth.MyUserDetails;
import digital.razgrad.LMP.entity.*;
import digital.razgrad.LMP.entity.Module;
import digital.razgrad.LMP.hellper.EntityValidator;
import digital.razgrad.LMP.repository.LectureRepository;
import digital.razgrad.LMP.repository.QuestionRepository;
import digital.razgrad.LMP.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

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
        boolean isTextExistForLecture = validateTestExistsForLecture(test);
        if (bindingResult.hasErrors() || !isEnoughQuestionsAvailable || isTextExistForLecture) {
            model.addAttribute("lectureList", lectureRepository.findAll());
            if (!isEnoughQuestionsAvailable) {
                model.addAttribute("message", "Зададеният брои въпроси е повече от наличните за тази лекция!");
            } else if (isTextExistForLecture) {
                model.addAttribute("message", "За тази лекция вече има създаден тест!");
            }
            return ("test/add");
        }
        redirectAttributes.addFlashAttribute("message", entityValidator.checkSaveSuccess(testRepository.save(test)));
        return "redirect:/test/add";
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

    public String startTest(Long id, Model model) {
        Optional<Test> optionalTest = testRepository.findById(id);
        if (optionalTest.isPresent()) {
           model.addAttribute("test", optionalTest.get());
           model.addAttribute("questionList", generateTest(optionalTest.get()));
           model.addAttribute("testResult", new TestResult());
        }
        return "/test/start";

    }

    public String finishTest(Test test, Authentication authentication, Model model) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return "/lecture/list";

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
    private Set<Question> generateTest (Test test){
        Set<Question> questionSet = new HashSet<>();
        List<Question> allQuestionByLecture = questionRepository.findByLecture(test.getLecture());
        int questionNumber = allQuestionByLecture.size();
        Random rand = new Random();
        while (questionSet.size() < test.getQuestionsNumber()) {
            int randomQuestionNumber = rand.nextInt(questionNumber);
            questionSet.add(allQuestionByLecture.get(randomQuestionNumber));
        }
        return questionSet;
    }

    private boolean validateEnoughQuestionsAvailable(Test test) {
        if (test.getQuestionsNumber() <= questionRepository.findByLecture(test.getLecture()).size()) {
            return true;
        }
        return false;
    }

    private boolean validateTestExistsForLecture(Test test) {
        Optional<Lecture> optionalLecture = lectureRepository.findById(test.getLecture().getId());
        if (optionalLecture.isPresent()) {
            if (optionalLecture.get().getTest() != null) {
                return true;
            }
        }
        return false;
    }
}
