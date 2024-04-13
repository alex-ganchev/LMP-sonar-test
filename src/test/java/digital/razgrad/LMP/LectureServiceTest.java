package digital.razgrad.LMP;

import digital.razgrad.LMP.entity.Course;
import digital.razgrad.LMP.entity.Lecture;
import digital.razgrad.LMP.entity.Module;
import digital.razgrad.LMP.entity.Student;
import digital.razgrad.LMP.hellper.EntityValidator;
import digital.razgrad.LMP.repository.CourseRepository;
import digital.razgrad.LMP.repository.LectureRepository;
import digital.razgrad.LMP.repository.ModuleRepository;
import digital.razgrad.LMP.repository.UserRepository;
import digital.razgrad.LMP.service.LectureService;
import digital.razgrad.LMP.service.ModuleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LectureServiceTest {
    @InjectMocks
    private LectureService lectureService;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private Model model;

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private ModuleRepository moduleRepository;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    BindingResult bindingResult;
    @Mock
    EntityValidator entityValidator;

    @Test
    public void testValidateSafeDeleteLectureWithNonEmptyLecture() {
        //GIVEN
        Lecture testLecture = new Lecture();
        testLecture.setQuestionSet(new HashSet<>());
        testLecture.setModule(new Module());
        testLecture.setTest(new digital.razgrad.LMP.entity.Test());
        when(lectureRepository.findById(1L)).thenReturn(Optional.of(testLecture));
        //WHEN
        boolean result = lectureService.validateSafeDeleteLecture(1L);
        //THEN
        assertFalse(result);
    }

    @Test
    public void testSaveLectureWithValidInput() {
        //GIVEN
        Lecture testLecture = new Lecture();
        when(bindingResult.hasErrors()).thenReturn(false);
        //WHEN
        lectureService.saveLecture(testLecture,bindingResult,redirectAttributes,model);
        //THEN
        verify(lectureRepository, times(1)).save(testLecture);
        verify(redirectAttributes).addFlashAttribute(anyString(),any());
    }
//    @Test
//    public void testSaveLectureWithInvalidInput() {
//        //GIVEN
//        Lecture testLecture = new Lecture();
//        testLecture.setQuestionSet(new HashSet<>());
//        Module module = new Module();
//        module.setId(1L);
//        testLecture.setModule(module);
//        when(bindingResult.hasErrors()).thenReturn(true);
//        //WHEN
//        lectureService.saveLecture(testLecture,bindingResult,redirectAttributes,model);
//        //THEN
//        verify(lectureRepository, never()).save(testLecture);
//        verify(model, times(2)).addAttribute(anyString(),any());
//    }
    @Test
    public void testUpdateLectureWithValidInput() {
        //GIVEN
        Lecture testLecture = new Lecture();
        when(bindingResult.hasErrors()).thenReturn(false);
        //WHEN
        lectureService.updateLecture(testLecture,bindingResult,redirectAttributes,model);
        //THEN
        verify(lectureRepository, times(1)).save(testLecture);
        verify(redirectAttributes).addFlashAttribute(anyString(),any());
    }
//    @Test
//    public void testUpdateModuleWithInvalidInput() {
//        //GIVEN
//        Lecture testLecture = new Lecture();
//        testLecture.setQuestionSet(new HashSet<>());
//        testLecture.setModule(new Module());
//        testLecture.setTest(new digital.razgrad.LMP.entity.Test());
//
//        when(bindingResult.hasErrors()).thenReturn(true);
//        //WHEN
//        lectureService.updateLecture(testLecture,bindingResult,redirectAttributes,model);
//        //THEN
//        verify(lectureRepository, never()).save(testLecture);
//        verify(model, times(3)).addAttribute(anyString(),any());
//    }

}
