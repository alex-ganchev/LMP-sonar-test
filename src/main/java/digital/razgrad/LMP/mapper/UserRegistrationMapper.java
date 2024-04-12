package digital.razgrad.LMP.mapper;

import digital.razgrad.LMP.dto.UserRegistrationDTO;
import digital.razgrad.LMP.entity.Student;
import digital.razgrad.LMP.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationMapper {
    public User toEntity (UserRegistrationDTO userRegistrationDTO){
        User user = new Student();
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setUsername(userRegistrationDTO.getUsername());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(userRegistrationDTO.getPassword());
        user.setAge(userRegistrationDTO.getAge());
        user.setCity(userRegistrationDTO.getCity());
        user.setInterests(userRegistrationDTO.getInterests());
        user.setExperience(userRegistrationDTO.getExperience());

        return user;
    }
}
