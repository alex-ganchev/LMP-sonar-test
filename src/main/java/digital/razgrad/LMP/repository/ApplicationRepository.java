package digital.razgrad.LMP.repository;

import digital.razgrad.LMP.entity.Application;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ApplicationRepository extends CrudRepository<Application, Long> {
    Optional<Application> findByStudentIdAndCourseId(Long studentId, Long CourseId);
}
