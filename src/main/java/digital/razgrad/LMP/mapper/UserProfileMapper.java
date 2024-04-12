package digital.razgrad.LMP.mapper;

import digital.razgrad.LMP.dto.UserProfileDTO;
import digital.razgrad.LMP.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {
    public UserProfileDTO toDto(User user){
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setId(user.getId());
        userProfileDTO.setUsername(user.getUsername());
        userProfileDTO.setFirstName(user.getFirstName());
        userProfileDTO.setLastName(user.getLastName());
        userProfileDTO.setEmail(user.getEmail());
        userProfileDTO.setCity(user.getCity());
        userProfileDTO.setAge(user.getAge());
        userProfileDTO.setUserRole(user.getUserRole());
        userProfileDTO.setExperience(user.getExperience());
        userProfileDTO.setInterests(user.getInterests());

        return userProfileDTO;
    }
}
