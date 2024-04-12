package digital.razgrad.LMP.hellper;

import digital.razgrad.LMP.entity.*;
import digital.razgrad.LMP.entity.Module;
import digital.razgrad.LMP.repository.*;
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
    @Autowired
    QuestionRepository questionRepository;
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


    public boolean checkSafeDeleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user instanceof Student && ((Student) user).getModules().isEmpty() && ((Student) user).getTestResults().isEmpty()){
                return true;
            } else if (user instanceof Teacher && ((Teacher) user).getCourses().isEmpty()) {
                return true;
            } else{
                return true;
            }
        }
        return false;
    }


}
