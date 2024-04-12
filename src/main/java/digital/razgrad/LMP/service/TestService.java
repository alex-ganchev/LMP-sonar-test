package digital.razgrad.LMP.service;

import digital.razgrad.LMP.auth.MyUserDetails;
import digital.razgrad.LMP.entity.*;
import digital.razgrad.LMP.hellper.EntityValidator;
import digital.razgrad.LMP.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Service
public class TestService {
    private LectureRepository lectureRepository;
    private EntityValidator entityValidator;
    private TestRepository testRepository;
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private TestResultRepository testResultRepository;
    private TestStudentAnswerRepository testStudentAnswerRepository;

    @Autowired
    private void setLectureRepository(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @Autowired
    private void setEntityValidator(EntityValidator entityValidator) {
        this.entityValidator = entityValidator;
    }

    @Autowired
    private void setTestRepository(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Autowired
    private void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private void setTestResultRepository(TestResultRepository testResultRepository) {
        this.testResultRepository = testResultRepository;
    }

    @Autowired
    private void setTestStudentAnswerRepository(TestStudentAnswerRepository testStudentAnswerRepository) {
        this.testStudentAnswerRepository = testStudentAnswerRepository;
    }

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

    public String finishTest(TestResult testResult, Authentication authentication, RedirectAttributes redirectAttributes, Model model) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        List<TestStudentAnswer> testStudentAnswerList = testResult.getTestStudentAnswerList();
        if (optionalUser.isPresent()) {
            testResult.setStudent((Student) optionalUser.get());
            Student student = (Student) optionalUser.get();
            saveTestResultWithAnswers(testResult, testStudentAnswerList,student);
            redirectAttributes.addFlashAttribute("message", entityValidator.checkSaveSuccess(saveTestResultWithAnswers(testResult, testStudentAnswerList,student)));
        }

        return "redirect:/";

    }
    @Transactional
    private TestResult saveTestResultWithAnswers(TestResult testResult, List<TestStudentAnswer> testStudentAnswerList, Student student) {
        TestResult savedTestResult = testResultRepository.save(testResult);
        for (TestStudentAnswer studentAnswer : testStudentAnswerList) {
            studentAnswer.setStudent(student);
            studentAnswer.setTestResult(savedTestResult);
            testStudentAnswerRepository.save(studentAnswer);
            }
        return savedTestResult;
    }

    public String deleteTest(Long id, RedirectAttributes redirectAttributes, Model model) {
        if (validateSafeDeleteTest(id)) {
            testRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", entityValidator.checkDeleteSuccess(testRepository.existsById(id)));
            return "redirect:/test/list";
        }
        redirectAttributes.addFlashAttribute("message", "Теста е част от решен тест. Не може да бъде изтрит!");
        return "redirect:/test/list";
    }

    public String checkTestResult(Long id, Model model) {
        Optional<TestResult> optionalTestResult = testResultRepository.findById(id);
        if (optionalTestResult.isPresent()) {
            model.addAttribute("testResult", optionalTestResult.get());
            return "/test/check";
        }
        return "redirect/test/result";
    }

    public String updateTestResult(TestResult testResult, RedirectAttributes redirectAttributes, Model model) {
        Optional<TestResult> optionalTestResult = testResultRepository.findById(testResult.getId());
        if (optionalTestResult.isPresent()) {
            List<TestStudentAnswer> testStudentAnswerList = optionalTestResult.get().getTestStudentAnswerList();
            for (int i = 0; i < testStudentAnswerList.size(); i++) {
                testStudentAnswerList.get(i).setCorrect(testResult.getTestStudentAnswerList().get(i).isCorrect());
            }
            optionalTestResult.get().setTestStudentAnswerList(testStudentAnswerList);
            testResultRepository.save(optionalTestResult.get());
            calculatePoint(optionalTestResult.get());
            redirectAttributes.addFlashAttribute("message", "Успешно проверихте теста!");
        }
        return "redirect:/test/result";
    }
    //TODO
    //Да се добави поле в testResult за максимален брой точки и процент на верните отговори и да се преправят/опростят методите.

    private void calculatePoint(TestResult testResult) {
        Optional<TestResult> optionalTestResult = testResultRepository.findById(testResult.getId());
        int studentTestPoints = 0;
        int maxTestPoints = 0;
        if (optionalTestResult.isPresent()) {
            TestResult newTestResult = optionalTestResult.get();
            for (TestStudentAnswer testStudentAnswer : newTestResult.getTestStudentAnswerList()) {
                maxTestPoints += testStudentAnswer.getQuestion().getPoints();
                if (testStudentAnswer.isCorrect()) {
                    studentTestPoints += testStudentAnswer.getQuestion().getPoints();
                }
            }
            newTestResult.setResult(studentTestPoints);
            boolean isPassed = isTestPassed(studentTestPoints, maxTestPoints, testResult.getTest().getMinPassingPercentage());
            newTestResult.setTestPassed(isPassed);
            testResultRepository.save(newTestResult);
        }
    }

    private boolean isTestPassed(int studentTestPoints, int maxTestPoints, int minPassingPercentage) {
        if ((studentTestPoints / (double) maxTestPoints * 100.00) >= minPassingPercentage) {
            return true;
        }
        return false;
    }

    private Set<Question> generateTest(Test test) {
        Set<Question> questionSet = new HashSet<>();
        List<Question> allQuestionByLecture = questionRepository.findByLecture(test.getLecture());
        int questionNumber = allQuestionByLecture.size();
        Random rand = new Random();
        while (questionSet.size() < test.getQuestionsNumber()) {
            int randomQuestionNumber = rand.nextInt(questionNumber);
            Question randomQuestion = allQuestionByLecture.get(randomQuestionNumber);
            List<Answer> questionAnswerList = randomQuestion.getAnswerList();
            List<Answer> questionRandomAnswerList = questionRandomAnswerList(questionAnswerList);
            randomQuestion.setAnswerList(questionRandomAnswerList);
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

    private boolean validateSafeDeleteTest(Long id) {
        Optional<Test> optionalTest = testRepository.findById(id);
        if (optionalTest.isPresent() && optionalTest.get().getTestResultList().isEmpty()) {
            return true;
        }
        return false;
    }

    private List<Answer> questionRandomAnswerList(List<Answer> answerList) {
        Set<Answer> answerSet = new HashSet<>();
        for (Answer answer : answerList) {
            answer.setCorrect(false);
            answerSet.add(answer);
        }
        return new ArrayList<>(answerSet);
    }
}
