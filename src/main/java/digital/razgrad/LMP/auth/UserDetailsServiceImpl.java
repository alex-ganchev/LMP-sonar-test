package digital.razgrad.LMP.auth;


import digital.razgrad.LMP.entity.User;
import digital.razgrad.LMP.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail)

            throws UsernameNotFoundException {
        User userByEmail = userRepository.getUserByEmail(usernameOrEmail);
        User userByUsername = userRepository.getUserByUsername(usernameOrEmail);
        if (userByUsername != null) {
            return new MyUserDetails(userByUsername);
        } else if (userByEmail != null) {
            return new MyUserDetails(userByEmail);
        } else {
            throw new UsernameNotFoundException("Could not find user");
        }
    }
}
