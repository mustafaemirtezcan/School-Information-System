package org.sms.schoolmanagementsystem.controller.impl;

import jakarta.validation.Valid;
import org.sms.schoolmanagementsystem.controller.ICourseController;
import org.sms.schoolmanagementsystem.dto.CourseDto;
import org.sms.schoolmanagementsystem.dto.CourseUpdateDto;
import org.sms.schoolmanagementsystem.services.ICourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/course")
public class CourseControllerImpl implements ICourseController {

    private final ICourseService courseService;
    public CourseControllerImpl(ICourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/add")
    @Override
    public CourseDto addCourse(@RequestBody  @Valid CourseUpdateDto courseUpdateDto) {
        return courseService.addCourse( courseUpdateDto);
    }
    @DeleteMapping("/delete/{id}")
    @Override
    public void deleteCourse(@PathVariable(name = "id") int id) {
        courseService.deleteCourse(id);
    }

    @PostMapping("{courseId}/enroll/students")
    @Override
    public CourseDto enrollStudentsToCourse(@PathVariable int courseId, @RequestBody List<Long> studentIds) {
        return courseService.enrollStudentsToCourse(courseId, studentIds);
    }

    @PostMapping("{courseId}/enroll/lecturers/")
    @Override
    public CourseDto enrollLecturersToCourse(@PathVariable int courseId, List<Long> lecturerIds) {
        return courseService.enrollLecturersToCourse(courseId, lecturerIds);
    }
    @GetMapping("/list")
    @Override
    public List<CourseDto> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/list/{id}")
    @Override
    public CourseDto getCourseById(@PathVariable(name = "id") int id) {
        return courseService.getCourseById(id);
    }
    @PutMapping("/update/{id}")
    @Override
    public CourseDto updateCourse(@PathVariable int id, @RequestBody @Valid CourseUpdateDto courseUpdateDto) {
        return courseService.updateCourse(id, courseUpdateDto);
    }

}
