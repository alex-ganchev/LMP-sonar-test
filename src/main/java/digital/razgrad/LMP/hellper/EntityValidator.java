package digital.razgrad.LMP.hellper;

import digital.razgrad.LMP.entity.Course;
import digital.razgrad.LMP.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntityValidator {
    @Autowired
    private CourseRepository courseRepository;
    public String checkSaveSuccess(Object object) {
        if (object != null) {
            return "Успешен запис в базата данни!";
        } else {
            return "Неуспешен запис в базата данни! :(";
        }
    }
    public String checkDeleteSuccess(boolean flag) {
        if (!flag) {
            return "Успешено изтриване от базата данни!";
        } else {
            return "Неуспешено изтриване от базата данни!";
        }
    }
    public boolean checkSafeDeleteCourse(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent() && optionalCourse.get().getModuleSet().isEmpty() && optionalCourse.get().getTeacherSet().isEmpty()) {
            return true;
        }
        return false;
    }
}
