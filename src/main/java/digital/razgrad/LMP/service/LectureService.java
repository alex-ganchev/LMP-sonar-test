package digital.razgrad.LMP.service;

import digital.razgrad.LMP.entity.Course;
import digital.razgrad.LMP.entity.Lecture;
import digital.razgrad.LMP.entity.Module;
import digital.razgrad.LMP.hellper.EntityValidator;
import digital.razgrad.LMP.repository.CourseRepository;
import digital.razgrad.LMP.repository.LectureRepository;
import digital.razgrad.LMP.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
public class LectureService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private EntityValidator entityValidator;

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
        return "redirect:/lecture/select-course";
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
        lectureRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", entityValidator.checkDeleteSuccess(lectureRepository.existsById(id)));
        return "redirect:/lecture/list";
    }
}
