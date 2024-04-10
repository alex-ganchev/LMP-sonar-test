package digital.razgrad.LMP.controller;

import digital.razgrad.LMP.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    private String addApplication(@RequestParam Long id, Authentication authentication, RedirectAttributes redirectAttributes, Model model) {
        return applicationService.addApplication(id, authentication, redirectAttributes, model);
    }

    @GetMapping("/list")
    private String listAllUnProvedApplications(Model model) {
        return applicationService.listAllUnProvedApplications(model);
    }

    @PostMapping("/approve")
    private String applicationApprove(@RequestParam Long id, RedirectAttributes redirectAttributes, Model model) {
        return applicationService.applicationApprove(id, redirectAttributes, model);
    }
}
