package org.sms.schoolmanagementsystem.services.impl;

import org.sms.schoolmanagementsystem.dto.CourseDto;
import org.sms.schoolmanagementsystem.dto.CourseUpdateDto;
import org.sms.schoolmanagementsystem.entity.Course;
import org.sms.schoolmanagementsystem.entity.Lecturer;
import org.sms.schoolmanagementsystem.entity.Student;
import org.sms.schoolmanagementsystem.exception.EntityNotFoundException.SchoolNotFoundException;
import org.sms.schoolmanagementsystem.repository.CourseRepository;
import org.sms.schoolmanagementsystem.repository.LecturerRepository;
import org.sms.schoolmanagementsystem.repository.SchoolRepository;
import org.sms.schoolmanagementsystem.repository.StudentRepository;
import org.sms.schoolmanagementsystem.services.ICourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseServiceImpl implements ICourseService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final LecturerRepository lecturerRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository, SchoolRepository schoolRepository, LecturerRepository lecturerRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
        this.lecturerRepository = lecturerRepository;
    }

    @Override
    public CourseDto addCourse(CourseUpdateDto courseUpdateDto) {
        Course course = uDtoToEntity(courseUpdateDto);
        Course savedCourse = courseRepository.save(course);
        return entityToDto(savedCourse);
    }

    @Override
    public CourseDto enrollStudentsToCourse(int courseId, List<Long> studentIds) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            for (Long studentId : studentIds) {
                Optional<Student> optionalStudent = studentRepository.findById(studentId.longValue());
                if (optionalStudent.isPresent()) {
                    Student student = optionalStudent.get();
                    student.getCourses().add(course);
                    course.getStudents().add(student);
                }
            }
            courseRepository.save(course);
            return entityToDto(course);
        }
        return null;
    }

    @Override
    public CourseDto enrollLecturersToCourse(int courseId, List<Long> lecturerIds) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            for (Long lecturerId : lecturerIds) {
                Optional<Lecturer> optionalLecturer = lecturerRepository.findById(lecturerId.longValue());
                if (optionalLecturer.isPresent()) {
                    Lecturer lecturer = optionalLecturer.get();
                    lecturer.getCourses().add(course);
                    course.getLecturers().add(lecturer);
                }
            }
            courseRepository.save(course);
            return entityToDto(course);
        }
        return null;
    }

    @Override
    public void deleteCourse(int id){
        Optional<Course> optionalCourse=courseRepository.findById(id);
        optionalCourse.ifPresent(courseRepository::delete);
    }

    @Override
    public List<CourseDto> getAllCourses() {
        List<CourseDto> courseDtos = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            CourseDto dtoCourse = entityToDto(course);
            courseDtos.add(dtoCourse);
        }
        return courseDtos;
    }

    @Override
    public CourseDto getCourseById(int id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            return entityToDto(course);
        }
        return null;
    }
    @Override
    public CourseDto updateCourse(int id,CourseUpdateDto courseUpdateDto) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course course = uDtoToEntity(courseUpdateDto);
            course.setId(id);
            Course updatedCourse=courseRepository.save(course);
            return entityToDto(updatedCourse);
        }
        return null;
    }

    public Course uDtoToEntity(CourseUpdateDto courseUpdateDto) {
        Course course = new Course();
        BeanUtils.copyProperties(courseUpdateDto, course);
        course.setSchool(schoolRepository.findById(courseUpdateDto.getSchoolId()).orElseThrow(() -> new SchoolNotFoundException("Course", courseUpdateDto.getSchoolId())));
        Set<Student> students = new HashSet<>();
        if(courseUpdateDto.getStudents() != null) {
            for (Long studentId : courseUpdateDto.getStudents()) {
                Optional <Student> optionalStudent = studentRepository.findById(studentId);
                if (optionalStudent.isPresent()) {
                    Student tempStudent = optionalStudent.get();
                    tempStudent.getCourses().add(course);
                    students.add(tempStudent);
                }
            }
        }
        course.setStudents(students);
        Set<Lecturer> lecturers = new HashSet<>();
        if(courseUpdateDto.getLecturers() != null) {
            for (Long lecturerId : courseUpdateDto.getLecturers()) {
                Optional<Lecturer> optionalLecturer = lecturerRepository.findById(lecturerId);
                if (optionalLecturer.isPresent()) {
                    Lecturer tempLecturer = optionalLecturer.get();
                    tempLecturer.getCourses().add(course);
                    lecturers.add(tempLecturer);
                }
            }
        }
        course.setLecturers(lecturers);
        return course;
    }
    public CourseDto entityToDto(Course course) {
        CourseDto courseDto = new CourseDto();
        BeanUtils.copyProperties(course, courseDto);
        return courseDto;
    }

}
