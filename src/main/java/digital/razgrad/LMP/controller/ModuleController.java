package digital.razgrad.LMP.controller;

import digital.razgrad.LMP.entity.Course;
import digital.razgrad.LMP.entity.Module;
import digital.razgrad.LMP.repository.CourseRepository;
import digital.razgrad.LMP.repository.ModuleRepository;
import digital.razgrad.LMP.service.ModuleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/module")
public class ModuleController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    ModuleRepository moduleRepository;

    @GetMapping("/add")
    private String addModule(Model model) {
        model.addAttribute("module", new Module());
        model.addAttribute("courses", courseRepository.findAll());
        return "/module/add";
    }

    @PostMapping("/add")
    private String saveModule(@Valid @ModelAttribute Module module, BindingResult bindingResult, Model model) {
        return moduleService.saveModule(module, bindingResult, model);
    }
    @GetMapping("/list")
    private String listAllModules(Model model) {
        model.addAttribute("moduleList", moduleRepository.findAll());
        return "/module/list";
    }
}
