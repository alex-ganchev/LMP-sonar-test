package digital.razgrad.LMP.entity;

import digital.razgrad.LMP.constant.UserRole;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Teacher")
public class Teacher extends User {
    @ManyToMany
//    @JoinTable(
//            name = "teachers_courses",
//            joinColumns = @JoinColumn(name = "teacher_id"),
//            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses;

    public Teacher() {
        setUserRole(UserRole.ROLE_TEACHER);
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
