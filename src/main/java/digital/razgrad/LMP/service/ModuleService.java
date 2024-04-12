package digital.razgrad.LMP.service;

import digital.razgrad.LMP.constant.CourseStatus;
import digital.razgrad.LMP.constant.CourseType;
import digital.razgrad.LMP.entity.Course;
import digital.razgrad.LMP.entity.Module;
import digital.razgrad.LMP.hellper.EntityValidator;
import digital.razgrad.LMP.repository.CourseRepository;
import digital.razgrad.LMP.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Service
public class ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private EntityValidator entityValidator;

    public String saveModule(Module module, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("courses", courseRepository.findAll());
            model.addAttribute("module", module);
            return "/module/add";
        }
        redirectAttributes.addFlashAttribute("message", entityValidator.checkSaveSuccess(moduleRepository.save(module)));
        return "redirect:/module/list";
    }

    public String editModule(@RequestParam Long id, Model model) {
        Optional<Module> optionalModule = moduleRepository.findById(id);
        if (optionalModule.isPresent()) {
            model.addAttribute("courseList", courseRepository.findAll());
            model.addAttribute("module", optionalModule.get());
            model.addAttribute("studentList", optionalModule.get().getCourse().getStudents());
            return "/module/edit";
        }
        return "redirect:/module/list";
    }

    public String updateModule(Module module, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("courseList", courseRepository.findAll());
            model.addAttribute("studentList", module.getCourse().getStudents());
            model.addAttribute("module", module);
            return "/module/edit";
        }
        redirectAttributes.addFlashAttribute("message", entityValidator.checkSaveSuccess(moduleRepository.save(module)));
        return "redirect:/module/list";
    }

    public String deleteModule(@RequestParam Long id, RedirectAttributes redirectAttributes, Model model) {
        if (validateSafeDeleteModule(id)) {
            moduleRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", entityValidator.checkDeleteSuccess(moduleRepository.existsById(id)));
            return "redirect:/module/list";
        }
        redirectAttributes.addFlashAttribute("message","Модула има записани курсисти и/или добавени лекции. Не може да бъде изтрит!");
        return "redirect:/module/list";
    }
    private boolean validateSafeDeleteModule(Long id) {
        Optional<Module> optionalModule = moduleRepository.findById(id);
        if (optionalModule.isPresent() && optionalModule.get().getStudents().isEmpty() && optionalModule.get().getLectureSet().isEmpty()) {
            return true;
        }
        return false;
    }
}
