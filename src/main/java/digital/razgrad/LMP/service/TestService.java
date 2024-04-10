package digital.razgrad.LMP.service;

import digital.razgrad.LMP.auth.MyUserDetails;
import digital.razgrad.LMP.entity.*;
import digital.razgrad.LMP.entity.Module;
import digital.razgrad.LMP.hellper.EntityValidator;
import digital.razgrad.LMP.repository.*;
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
    private LectureRepository lectureRepository;
    @Autowired
    private EntityValidator entityValidator;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestResultRepository testResultRepository;
    @Autowired
    private TestStudentAnswerRepository testStudentAnswerRepository;


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

    public String finishTest(TestResult testResult, Authentication authentication, Model model) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        List<TestStudentAnswer> testStudentAnswerList = testResult.getTestStudentAnswerList();
        if (optionalUser.isPresent()) {
            testResult.setStudent((Student) optionalUser.get());
            TestResult savedTestResult = testResultRepository.save(testResult);
            for (TestStudentAnswer studentAnswer : testStudentAnswerList) {
                studentAnswer.setStudent((Student) optionalUser.get());
                studentAnswer.setTestResult(savedTestResult);
                testStudentAnswerRepository.save(studentAnswer);
            }
        }
        return "/index";

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
    public String checkTestResult(Long id, Model model) {
       Optional<TestResult> optionalTestResult = testResultRepository.findById(id);
       if(optionalTestResult.isPresent()){
           model.addAttribute("testResult", optionalTestResult.get());
           return "/test/check";
       }
        return "redirect/test/result";
    }

    private Set<Question> generateTest(Test test) {
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
