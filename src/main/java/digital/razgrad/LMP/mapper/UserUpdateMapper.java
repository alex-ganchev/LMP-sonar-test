package digital.razgrad.LMP.mapper;

import digital.razgrad.LMP.constant.UserRole;
import digital.razgrad.LMP.dto.UserUpdateDTO;
import digital.razgrad.LMP.entity.Admin;
import digital.razgrad.LMP.entity.Student;
import digital.razgrad.LMP.entity.Teacher;
import digital.razgrad.LMP.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserUpdateMapper {
    public UserUpdateDTO toDTO(User user) {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setId(user.getId());
        userUpdateDTO.setFirstName(user.getFirstName());
        userUpdateDTO.setLastName(user.getLastName());
        userUpdateDTO.setUsername(user.getUsername());
        userUpdateDTO.setEmail(user.getEmail());
        userUpdateDTO.setAge(user.getAge());
        userUpdateDTO.setCity(user.getCity());
        userUpdateDTO.setInterests(user.getInterests());
        userUpdateDTO.setExperience(user.getExperience());
        userUpdateDTO.setUserRole(user.getUserRole());

        return userUpdateDTO;
    }

    public User toEntity(UserUpdateDTO userUpdateDTO) {
        User user;
        if (userUpdateDTO.getUserRole().equals(UserRole.ROLE_ADMIN)) {
            user = new Admin();
        } else if (userUpdateDTO.getUserRole().equals(UserRole.ROLE_TEACHER)) {
            user = new Teacher();
        } else {
            user = new Student();
        }
        user.setId(userUpdateDTO.getId());
        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setUsername(userUpdateDTO.getUsername());
        user.setEmail(userUpdateDTO.getEmail());
        user.setPassword(userUpdateDTO.getPassword());
        user.setAge(userUpdateDTO.getAge());
        user.setCity(userUpdateDTO.getCity());
        user.setInterests(userUpdateDTO.getInterests());
        user.setExperience(userUpdateDTO.getExperience());
        user.setUserRole(userUpdateDTO.getUserRole());

        return user;
    }
}
