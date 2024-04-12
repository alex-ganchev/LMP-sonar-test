package digital.razgrad.LMP.controller;

import digital.razgrad.LMP.entity.Module;
import digital.razgrad.LMP.repository.CourseRepository;
import digital.razgrad.LMP.repository.ModuleRepository;
import digital.razgrad.LMP.service.ModuleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/module")
public class ModuleController {

    private CourseRepository courseRepository;

    private ModuleService moduleService;

    private ModuleRepository moduleRepository;

    @Autowired
    private void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    private void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @Autowired
    private void setModuleRepository(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @GetMapping("/add")
    private String addModule(Model model) {
        model.addAttribute("module", new Module());
        model.addAttribute("courses", courseRepository.findAll());
        return "/module/add";
    }

    @PostMapping("/add")
    private String saveModule(@Valid @ModelAttribute Module module, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        return moduleService.saveModule(module, bindingResult, redirectAttributes, model);
    }

    @GetMapping("/list")
    private String listAllModules(Model model) {
        model.addAttribute("moduleList", moduleRepository.findAll());
        return "/module/list";
    }

    @GetMapping("/edit")
    private String editModule(@RequestParam Long id, Model model) {
        return moduleService.editModule(id, model);
    }

    @PostMapping("/edit")
    private String updateModule(@Valid @ModelAttribute Module module, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        return moduleService.updateModule(module, bindingResult, redirectAttributes, model);
    }

    @PostMapping("/delete")
    private String deleteModule(@RequestParam Long id, RedirectAttributes redirectAttributes, Model model) {
        return moduleService.deleteModule(id, redirectAttributes, model);
    }
}
