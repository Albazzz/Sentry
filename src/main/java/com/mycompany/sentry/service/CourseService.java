package com.mycompany.sentry.service;

import com.mycompany.sentry.entity.Course;
import com.mycompany.sentry.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Optional<Course> findById(Integer id) {
        return courseRepository.findById(id);
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void deleteById(Integer id) {
        courseRepository.deleteById(id);
    }

    public List<Course> findByCreatedBy(Integer createdBy) {
        return courseRepository.findByCreatedBy(createdBy);
    }

    public List<Course> findVisibleCourses() {
        return courseRepository.findByIsHidden(false);
    }

    public List<Course> findSuggestedCourses() {
        return courseRepository.findByIsSuggested(true);
    }

    public List<Course> findSuggestedAndVisibleCourses() {
        return courseRepository.findByIsHiddenAndIsSuggested(false, true);
    }

    public List<Course> searchCourses(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return findAll();
        }
        return courseRepository.searchByTitleContainingIgnoreCase(keyword);
    }

    public List<Course> searchVisibleCourses(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return findVisibleCourses();
        }
        return courseRepository.searchVisibleByTitleContainingIgnoreCase(keyword);
    }
}