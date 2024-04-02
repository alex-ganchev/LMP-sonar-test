package digital.razgrad.LMP.service;

import digital.razgrad.LMP.constant.CourseStatus;
import digital.razgrad.LMP.constant.CourseType;
import digital.razgrad.LMP.entity.Module;
import digital.razgrad.LMP.repository.CourseRepository;
import digital.razgrad.LMP.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private CourseRepository courseRepository;

    public String saveModule(Module module, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("courses", courseRepository.findAll());
            return "/module/add";
        }
        moduleRepository.save(module);
        return "redirect:/module/add";
    }
}
