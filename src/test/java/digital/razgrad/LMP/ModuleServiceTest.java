package digital.razgrad.LMP;

import digital.razgrad.LMP.entity.*;
import digital.razgrad.LMP.entity.Module;
import digital.razgrad.LMP.hellper.EntityValidator;
import digital.razgrad.LMP.repository.CourseRepository;
import digital.razgrad.LMP.repository.ModuleRepository;
import digital.razgrad.LMP.repository.UserRepository;
import digital.razgrad.LMP.service.CourseService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ModuleServiceTest {
    @InjectMocks
    private ModuleService moduleService;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private Model model;

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private ModuleRepository moduleRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    BindingResult bindingResult;
    @Mock
    EntityValidator entityValidator;

    @Test
    public void testValidateSafeDeleteModuleWithNonEmptyModule() {
        //GIVEN
        Module testModule = new Module();
        testModule.setStudents(new HashSet<>());
        testModule.setLectureSet(new HashSet<>());
        testModule.setCourse(new Course());
        testModule.getLectureSet().add(new Lecture());
        testModule.getStudents().add(new Student());
        when(moduleRepository.findById(1L)).thenReturn(Optional.of(testModule));
        //WHEN
        boolean result = moduleService.validateSafeDeleteModule(1L);
        //THEN
        assertFalse(result);
    }

    @Test
    public void testSaveModuleWithValidInput() {
        //GIVEN
        Module testModule = new Module();
        when(bindingResult.hasErrors()).thenReturn(false);
        //WHEN
        moduleService.saveModule(testModule,bindingResult,redirectAttributes,model);
        //THEN
        verify(moduleRepository, times(1)).save(testModule);
        verify(redirectAttributes).addFlashAttribute(anyString(),any());
    }
    @Test
    public void testSaveModuleWithInvalidInput() {
        //GIVEN
        Module testModule = new Module();
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        moduleService.saveModule(testModule,bindingResult,redirectAttributes,model);
        //THEN
        verify(moduleRepository, never()).save(testModule);
        verify(model, times(2)).addAttribute(anyString(),any());
    }
    @Test
    public void testUpdateModuleWithValidInput() {
        //GIVEN
        Module testModule = new Module();
        when(bindingResult.hasErrors()).thenReturn(false);
        //WHEN
        moduleService.updateModule(testModule,bindingResult,redirectAttributes,model);
        //THEN
        verify(moduleRepository, times(1)).save(testModule);
        verify(redirectAttributes).addFlashAttribute(anyString(),any());
    }
    @Test
    public void testUpdateModuleWithInvalidInput() {
        //GIVEN
        Module testModule = new Module();
        testModule.setStudents(new HashSet<>());
        testModule.setLectureSet(new HashSet<>());
        testModule.setCourse(new Course());

        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        moduleService.updateModule(testModule,bindingResult,redirectAttributes,model);
        //THEN
        verify(moduleRepository, never()).save(testModule);
        verify(model, times(3)).addAttribute(anyString(),any());
    }

}
