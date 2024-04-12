package digital.razgrad.LMP;


import digital.razgrad.LMP.dto.UserRegistrationDTO;
import digital.razgrad.LMP.entity.Student;
import digital.razgrad.LMP.entity.User;
import digital.razgrad.LMP.mapper.UserRegistrationMapper;
import digital.razgrad.LMP.repository.UserRepository;
import digital.razgrad.LMP.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    UserRegistrationMapper userRegistrationMapper;
    @Mock
    BindingResult bindingResult;
    @Mock
    Model model;

    @Test
    void testAddUserReturnValidResultWithCorrectInput() {
        //GIVEN
        User testUser = new Student();
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        testUser.setPassword("1234");
        userRegistrationDTO.setPassword("1234");
        userRegistrationDTO.setRepeatPassword("1234");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userRegistrationMapper.toEntity(userRegistrationDTO)).thenReturn(testUser);
        //WHEN
        userService.saveUser(userRegistrationDTO,bindingResult,model);
        //THEN
        verify(userRepository, times(1)).save(testUser);
        verify(model, never()).addAttribute(anyString(),any());
    }
    @Test
    void testAddUserReturnValidResultWithValidationErrorInput() {
        //GIVEN
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setPassword("1234");
        userRegistrationDTO.setRepeatPassword("1234");
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        userService.saveUser(userRegistrationDTO,bindingResult,model);
        //THEN
        verify(userRepository,never()).save(any());
        verify(model, times(1)).addAttribute(anyString(),any());

    }
    @Test
    void testAddUserReturnValidResultWithDifferentPassword() {
        //GIVEN
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setPassword("1111");
        userRegistrationDTO.setRepeatPassword("1234");
        //WHEN
        userService.saveUser(userRegistrationDTO,bindingResult,model);
        //THEN
        verify(userRepository,never()).save(any());
        verify(model, times(1)).addAttribute(anyString(),any());
    }

}
