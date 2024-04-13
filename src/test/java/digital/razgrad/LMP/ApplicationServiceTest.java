package digital.razgrad.LMP;

import digital.razgrad.LMP.auth.MyUserDetails;
import digital.razgrad.LMP.entity.Application;
import digital.razgrad.LMP.repository.ApplicationRepository;
import digital.razgrad.LMP.repository.CourseRepository;
import digital.razgrad.LMP.repository.UserRepository;
import digital.razgrad.LMP.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {
    @InjectMocks
    private ApplicationService applicationService;
    @Mock
    private Authentication authentication;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private Model model;

    @Mock
    private MyUserDetails userDetails;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @Test
    void testAddApplicationWithCorrectInput() {
        //GIVEN
//        when(authentication.getPrincipal()).thenReturn(userDetails);
//        when(userDetails.getId()).thenReturn(1L);
//        Course course = new Course();
//        when(courseRepository.findById(any())).thenReturn(Optional.of(course));
//        User user = new Student();
//        when(userRepository.findById(any())).thenReturn(Optional.of(user));
//        Application application = new Application();
//        when(applicationRepository.findByStudentIdAndCourseId(any(), any())).thenReturn(Optional.of(application));
//        when(applicationService.validateDuplicateApplication(any(), any())).thenReturn(false);
//        //WHEN
//        applicationService.addApplication(1L, authentication, redirectAttributes, model);
//        //THEN
//        verify(applicationRepository, times(1)).save(any());
//        verify(redirectAttributes, times(1)).addAttribute(anyString(), any());
    }
    @Test
    void testListAllUnProvedApplications(){
        //GIVEN
       // List<Application> applicationsList = new ArrayList<>();
        when(applicationRepository.findAll()).thenReturn(new ArrayList<Application>());
        //WHEN
        String result = applicationService.listAllUnProvedApplications(model);
        //THEN
        verify(model, times(1)).addAttribute(anyString(),any());
        assertEquals("/application/list", result);
    }

    @Test
    void testValidateDuplicateApplicationWithDuplicateApplication(){
        //GIVEN
//        Application application = new Application();
//        Optional<Application> optionalApplication = Optional.of(application);
        when(applicationRepository.findByStudentIdAndCourseId(1L,1L)).thenReturn(Optional.of(new Application()));
        //WHEN
        boolean result = applicationService.validateDuplicateApplication(1L,1L);
        //THEN
        assertFalse(result);
    }
    @Test
    void testValidateDuplicateApplicationWithNonDuplicateApplication(){
        //GIVEN
       // Optional<Application> optionalApplication = Optional.empty();
        when(applicationRepository.findByStudentIdAndCourseId(1L,1L)).thenReturn(Optional.empty());
        //WHEN
        boolean result =applicationService.validateDuplicateApplication(1L,1L);
        //THEN
        assertTrue(result);
    }

}
