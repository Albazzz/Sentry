package com.mycompany.sentry.controller;

import com.mycompany.sentry.entity.Course;
import com.mycompany.sentry.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/searchCourse")
    public ResponseEntity<List<Course>> searchCourses(@RequestParam("query") String query) {
        List<Course> courses = courseService.searchVisibleCourses(query);
        return ResponseEntity.ok(courses);
    }

}