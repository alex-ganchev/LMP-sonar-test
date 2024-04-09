package digital.razgrad.LMP.repository;

import digital.razgrad.LMP.constant.UserRole;
import digital.razgrad.LMP.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    public User getUserByUsername(String name);

    public User getUserByEmail(String email);
    public User getUserByUserRole(UserRole role);
}
