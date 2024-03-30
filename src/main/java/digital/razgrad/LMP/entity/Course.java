package digital.razgrad.LMP.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Module> moduleSet;
    @ManyToMany(mappedBy = "courseSet")
    private Set<Teacher> teacherSet;
}
