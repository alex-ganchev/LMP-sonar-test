package digital.razgrad.LMP.repository;

import digital.razgrad.LMP.entity.Lecture;
import digital.razgrad.LMP.entity.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question,Long> {
    List<Question> findByLecture(Lecture lecture);
}
