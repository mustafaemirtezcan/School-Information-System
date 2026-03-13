package org.sms.schoolmanagementsystem.repository;

import org.sms.schoolmanagementsystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
