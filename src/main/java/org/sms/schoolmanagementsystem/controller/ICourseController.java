package org.sms.schoolmanagementsystem.controller;

import org.sms.schoolmanagementsystem.dto.CourseDto;
import org.sms.schoolmanagementsystem.dto.CourseUpdateDto;

import java.util.List;
import java.util.Set;

public interface ICourseController {

    CourseDto addCourse (CourseUpdateDto courseUpdateDto);
    void deleteCourse(int id);
    CourseDto enrollStudentsToCourse(int courseId, List<Long> studentIds);
    CourseDto enrollLecturersToCourse(int courseId, List<Long> lecturerIds);
    List<CourseDto> getAllCourses();
    CourseDto getCourseById(int id);
    CourseDto updateCourse(int id, CourseUpdateDto courseUpdateDto);
}
