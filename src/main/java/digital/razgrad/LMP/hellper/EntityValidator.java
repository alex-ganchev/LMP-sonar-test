package digital.razgrad.LMP.hellper;

import digital.razgrad.LMP.entity.*;
import digital.razgrad.LMP.entity.Module;
import digital.razgrad.LMP.repository.CourseRepository;
import digital.razgrad.LMP.repository.LectureRepository;
import digital.razgrad.LMP.repository.ModuleRepository;
import digital.razgrad.LMP.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntityValidator {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    private UserRepository userRepository;
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
    public boolean checkSafeDeleteModule(Long id) {
        Optional<Module> optionalModule = moduleRepository.findById(id);
        if (optionalModule.isPresent() && optionalModule.get().getStudentSet().isEmpty() && optionalModule.get().getLectureSet().isEmpty()) {
            return true;
        }
        return false;
    }
    public boolean checkSafeDeleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user instanceof Student && ((Student) user).getModuleSet().isEmpty() && ((Student) user).getTestSet().isEmpty()){
                return true;
            } else if (user instanceof Teacher && ((Teacher) user).getCourseSet().isEmpty()) {
                return true;
            } else{
                return true;
            }
        }
        return false;
    }
}
