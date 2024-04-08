package digital.razgrad.LMP.repository;

import digital.razgrad.LMP.entity.Lecture;
import digital.razgrad.LMP.entity.Module;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface LectureRepository extends CrudRepository<Lecture,Long> {
    @Query(value = "SELECT * FROM modules WHERE course_id = :id", nativeQuery = true)
    List<Module> findModulesByCourseId( @Param("id") Long id);
}
