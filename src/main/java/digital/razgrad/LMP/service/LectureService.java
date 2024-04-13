package digital.razgrad.LMP.service;

import digital.razgrad.LMP.auth.MyUserDetails;
import digital.razgrad.LMP.constant.UserRole;
import digital.razgrad.LMP.entity.Course;
import digital.razgrad.LMP.entity.Lecture;
import digital.razgrad.LMP.entity.Module;
import digital.razgrad.LMP.hellper.EntityValidator;
import digital.razgrad.LMP.repository.CourseRepository;
import digital.razgrad.LMP.repository.LectureRepository;
import digital.razgrad.LMP.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
public class LectureService {
    private CourseRepository courseRepository;
    private ModuleRepository moduleRepository;
    private LectureRepository lectureRepository;
    private EntityValidator entityValidator;

    @Autowired
    private void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    private void setModuleRepository(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Autowired
    private void setLectureRepository(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @Autowired
    private void setEntityValidator(EntityValidator entityValidator) {
        this.entityValidator = entityValidator;
    }

    public String selectCourse(Long id, RedirectAttributes redirectAttributes, Model model) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent() && !optionalCourse.get().getModuleSet().isEmpty()) {
            model.addAttribute("moduleList", optionalCourse.get().getModuleSet());
            model.addAttribute("lecture", new Lecture());
            return "/lecture/add";
        }
        redirectAttributes.addFlashAttribute("message", "Избраният курс няма модули. Не можете да въвеждате лекции!");
        return "redirect:/lecture/select-course";
    }

    public String listAllLecture(Model model, Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        if (userDetails.getRole().equals(UserRole.ROLE_STUDENT)) {
            List<Module> moduleList = moduleRepository.getModuleByStudentsId(userDetails.getId());
            model.addAttribute("lectureList", lectureRepository.getLectureByModule(moduleList.get(0)));
        } else {
            model.addAttribute("lectureList", lectureRepository.findAll());
        }
        return "/lecture/list";
    }

    public String saveLecture(Lecture lecture, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            Optional<Module> optionalModule = moduleRepository.findById(lecture.getModule().getId());
            if (optionalModule.isPresent()) {
                Optional<Course> optionalCourse = courseRepository.findById(optionalModule.get().getCourse().getId());
                if (optionalCourse.isPresent()) {
                    model.addAttribute("moduleList", optionalCourse.get().getModuleSet());
                    model.addAttribute("lecture", lecture);
                    return "/lecture/add";
                }
            }
            return "redirect:/lecture/select-course";
        }
        redirectAttributes.addFlashAttribute("message", entityValidator.checkSaveSuccess(lectureRepository.save(lecture)));
        return "redirect:/lecture/list";
    }

    public String editLecture(@RequestParam Long id, Model model) {
        Optional<Lecture> optionalLecture = lectureRepository.findById(id);
        if (optionalLecture.isPresent()) {
            Optional<Course> optionalCourse = courseRepository.findById(optionalLecture.get().getModule().getCourse().getId());
            if (optionalCourse.isPresent()) {
                model.addAttribute("moduleList", optionalCourse.get().getModuleSet());
                model.addAttribute("lecture", optionalLecture.get());
                return "/lecture/edit";
            }
        }
        return "redirect:/lecture/list";
    }

    public String updateLecture(Lecture lecture, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            Optional<Module> optionalModule = moduleRepository.findById(lecture.getModule().getId());
            if (optionalModule.isPresent()) {
                Optional<Course> optionalCourse = courseRepository.findById(optionalModule.get().getCourse().getId());
                if (optionalCourse.isPresent()) {
                    model.addAttribute("moduleList", optionalCourse.get().getModuleSet());
                    model.addAttribute("lecture", lecture);
                    return "/lecture/edit";
                }
            }
            return "redirect:/lecture/list";
        }
        redirectAttributes.addFlashAttribute("message", entityValidator.checkSaveSuccess(lectureRepository.save(lecture)));
        return "redirect:/lecture/list";
    }

    public String deleteLecture(@RequestParam Long id, RedirectAttributes redirectAttributes, Model model) {
        if (validateSafeDeleteLecture(id)) {
            lectureRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", entityValidator.checkDeleteSuccess(lectureRepository.existsById(id)));
        } else {
            redirectAttributes.addFlashAttribute("message", "За лекцията има добавени тестове и/или въпроси. Не може да бъде изтрита! ");
        }
        return "redirect:/lecture/list";
    }

    public boolean validateSafeDeleteLecture(Long id) {
        Optional<Lecture> optionalLecture = lectureRepository.findById(id);
        if (optionalLecture.isPresent() && optionalLecture.get().getQuestionSet().isEmpty() && optionalLecture.get().getTest() == null) {
            return true;
        }
        return false;
    }
}
