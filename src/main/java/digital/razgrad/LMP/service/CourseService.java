package digital.razgrad.LMP.service;

import digital.razgrad.LMP.constant.CourseStatus;
import digital.razgrad.LMP.constant.CourseType;
import digital.razgrad.LMP.entity.Course;
import digital.razgrad.LMP.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public String saveCourse(Course course, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("typeList", CourseType.values());
            model.addAttribute("statusList", CourseStatus.values());
            return "/course/add";
        }
        course.setStatus(CourseStatus.UPCOMING);
        courseRepository.save(course);
        return "redirect:/course/add";
    }
}
