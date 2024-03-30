package digital.razgrad.LMP.entity;

import digital.razgrad.LMP.constant.Role;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@DiscriminatorValue("Student")
public class Student extends User {

    @ManyToMany
    @JoinTable(
            name = "students_tests",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "test_id"))
    private Set<Test> testSet;
    @ManyToMany
    @JoinTable(
            name = "students_modules",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id"))
    private Set<Module> moduleSet;

    public Student() {
        setRole(Role.ROLE_STUDENT);
    }

}
