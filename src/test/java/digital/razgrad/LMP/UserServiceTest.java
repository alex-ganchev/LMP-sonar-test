package digital.razgrad.LMP;


import digital.razgrad.LMP.auth.MyUserDetails;
import digital.razgrad.LMP.dto.UserProfileDTO;
import digital.razgrad.LMP.dto.UserRegistrationDTO;
import digital.razgrad.LMP.entity.Student;
import digital.razgrad.LMP.entity.User;
import digital.razgrad.LMP.mapper.UserProfileMapper;
import digital.razgrad.LMP.mapper.UserRegistrationMapper;
import digital.razgrad.LMP.repository.UserRepository;
import digital.razgrad.LMP.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Mock
    Authentication authentication;
    @Mock
    MyUserDetails userDetails;
    @Mock
    UserProfileMapper userProfileMapper;

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
    @Test
    void testShowProfileReturnValidResultWithValidInput() {
        //GIVEN
        when(authentication.getPrincipal()).thenReturn(userDetails);
        User user = new Student();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        //WHEN
        userService.showProfile(authentication, model);
        //THEN
        verify(model, times(1)).addAttribute(anyString(),any());
    }
    @Test
    void testShowProfileReturnValidResultWithInvalidInput() {
        //GIVEN
        when(authentication.getPrincipal()).thenReturn(userDetails);
        User user = new Student();
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        //WHEN
        userService.showProfile(authentication, model);
        //THEN
        verify(model, never()).addAttribute(anyString(),any());
    }
    @Test
    void testListAllProfileReturnValidResultWithValidInput() {
        //GIVEN
        User user1 = new Student();
        User user2 = new Student();
        List<User> userList = new ArrayList<>();
        user1.setId(1L);
        user2.setId(2L);
        userList.add(user1);
        userList.add(user2);
        when(userRepository.findAll()).thenReturn(userList);
        UserProfileDTO userProfileDTO1 = new UserProfileDTO();
        UserProfileDTO userProfileDTO2 = new UserProfileDTO();
        when(userProfileMapper.toDto(user1)).thenReturn(userProfileDTO1);
        when(userProfileMapper.toDto(user2)).thenReturn(userProfileDTO2);
        //WHEN
        userService.listAllUsers(model);
        //THEN
        verify(model,times(1)).addAttribute("userList", List.of(userProfileDTO1, userProfileDTO2));
    }

}
