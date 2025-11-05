package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    List<Enrollment> findByUserID(Integer userID);
    List<Enrollment> findByCourseID(Integer courseID);
    boolean existsByUserIDAndCourseID(Integer userID, Integer courseID);
}



