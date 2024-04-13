package digital.razgrad.LMP;

import digital.razgrad.LMP.entity.Course;
import digital.razgrad.LMP.entity.Module;
import digital.razgrad.LMP.entity.Student;
import digital.razgrad.LMP.entity.Teacher;
import digital.razgrad.LMP.hellper.EntityValidator;
import digital.razgrad.LMP.repository.CourseRepository;
import digital.razgrad.LMP.repository.UserRepository;
import digital.razgrad.LMP.service.CourseService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @InjectMocks
    private CourseService courseService;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private Model model;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    BindingResult bindingResult;
    @Mock
    EntityValidator entityValidator;


    @Test
    public void testValidateSafeDeleteCourseWithEmptyCourse() {
        //GIVEN
        Course course = new Course();
        course.setModuleSet(new HashSet<>());
        course.setTeachers(new HashSet<>());
        course.setStudentSet(new HashSet<>());
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        //WHEN
        boolean result = courseService.validateSafeDeleteCourse(1L);
        //THEN
        assertTrue(result);
    }
    @Test
    public void testValidateSafeDeleteCourseWithNonEmptyCourse() {
        //GIVEN
        Course course = new Course();
        course.setModuleSet(new HashSet<>());
        course.setTeachers(new HashSet<>());
        course.setStudentSet(new HashSet<>());
        course.getModuleSet().add(new Module());
        course.getTeachers().add(new Teacher());
        course.getStudents().add(new Student());
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        //WHEN
        boolean result = courseService.validateSafeDeleteCourse(1L);
        //THEN
        assertFalse(result);
    }

    @Test
    public void testAddCourse() {
        //GIVEN
        //WHEN
        courseService.addCourse(model);
        //THEN
        verify(model, times(3)).addAttribute(anyString(),any());
    }

    @Test
    public void testSaveCourseWithValidInput() {
        //GIVEN
        Course testCourse = new Course();
        when(bindingResult.hasErrors()).thenReturn(false);
        //WHEN
        courseService.saveCourse(testCourse,bindingResult,redirectAttributes,model);
        //THEN
        verify(courseRepository, times(1)).save(testCourse);
        verify(redirectAttributes).addFlashAttribute(anyString(),any());
    }
    @Test
    public void testSaveCourseWithInvalidInput() {
        //GIVEN
        Course testCourse = new Course();
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        courseService.saveCourse(testCourse,bindingResult,redirectAttributes,model);
        //THEN
        verify(courseRepository, never()).save(testCourse);
        verify(model, times(2)).addAttribute(anyString(),any());
    }
    @Test
    public void testUpdateCourseWithValidInput() {
        //GIVEN
        Course testCourse = new Course();
        when(bindingResult.hasErrors()).thenReturn(false);
        //WHEN
        courseService.updateCourse(testCourse,bindingResult,redirectAttributes,model);
        //THEN
        verify(courseRepository, times(1)).save(testCourse);
        verify(redirectAttributes).addFlashAttribute(anyString(),any());
    }
    @Test
    public void testUpdateCourseWithInvalidInput() {
        //GIVEN
        Course testCourse = new Course();
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        courseService.updateCourse(testCourse,bindingResult,redirectAttributes,model);
        //THEN
        verify(courseRepository, never()).save(testCourse);
        verify(model, times(3)).addAttribute(anyString(),any());
    }

}
