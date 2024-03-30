package digital.razgrad.LMP.entity;

import digital.razgrad.LMP.constant.Role;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@DiscriminatorValue("Teacher")
public class Teacher extends User {
    @ManyToMany
    @JoinTable(
            name = "teachers_courses",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courseSet;

    public Teacher() {
        setRole(Role.ROLE_TEACHER);
    }
}
