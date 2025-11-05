package com.mycompany.sentry.controller;

import com.mycompany.sentry.entity.Course;
import com.mycompany.sentry.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public String getAllCourses(Model model) {
        List<Course> courses = courseRepository.findByIsHiddenFalse();
        model.addAttribute("courses", courses);
        return "courses/list";
    }

    @GetMapping("/suggested")
    public String getSuggestedCourses(Model model) {
        List<Course> courses = courseRepository.findByIsSuggestedTrue();
        model.addAttribute("courses", courses);
        return "courses/suggested";
    }

    @GetMapping("/{id}")
    public String getCourseById(@PathVariable Integer id, Model model) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            return "redirect:/courses";
        }
        model.addAttribute("course", course);
        return "courses/detail";
    }
}




