package digital.razgrad.LMP.entity;

import digital.razgrad.LMP.constant.CourseStatus;
import digital.razgrad.LMP.constant.CourseType;
import jakarta.persistence.*;

import java.time.LocalDate;
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
    @Enumerated(EnumType.STRING)
    private CourseType type;
    @Enumerated(EnumType.STRING)
    private CourseStatus status;
    @Column(nullable = true)
    private String city;
    private LocalDate startDate;
}
