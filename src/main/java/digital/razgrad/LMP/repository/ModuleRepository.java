package digital.razgrad.LMP.repository;

import digital.razgrad.LMP.entity.Module;
import digital.razgrad.LMP.entity.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ModuleRepository extends CrudRepository<Module, Long> {
    public List<Module> getModuleByStudentsId(Long id);
}
