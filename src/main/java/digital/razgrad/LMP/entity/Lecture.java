package digital.razgrad.LMP.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="lectures")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true)
    private String presentation;
    @Column(nullable = true)
    private String video;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private Test test;
    private LocalDate startDate;
    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

}
