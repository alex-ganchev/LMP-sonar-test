package digital.razgrad.LMP.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="modules")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDate startDate;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @ManyToMany(mappedBy = "moduleSet")
    private Set<Student> studentSet;
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    private Set<Lecture> lectureSet;
}
