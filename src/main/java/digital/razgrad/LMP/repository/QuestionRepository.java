package digital.razgrad.LMP.repository;

import digital.razgrad.LMP.entity.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question,Long> {
}
