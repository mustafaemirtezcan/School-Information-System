package org.sms.schoolmanagementsystem.services.impl;

import jakarta.transaction.Transactional;
import org.sms.schoolmanagementsystem.exception.EntityNotFoundException.AddressNotFoundException;
import org.sms.schoolmanagementsystem.exception.EntityNotFoundException.SchoolNotFoundException;
import org.sms.schoolmanagementsystem.dto.StudentDto;
import org.sms.schoolmanagementsystem.dto.StudentUpdateDto;
import org.sms.schoolmanagementsystem.entity.Course;
import org.sms.schoolmanagementsystem.entity.Student;
import org.sms.schoolmanagementsystem.repository.AddressRepository;
import org.sms.schoolmanagementsystem.repository.CourseRepository;
import org.sms.schoolmanagementsystem.repository.SchoolRepository;
import org.sms.schoolmanagementsystem.repository.StudentRepository;
import org.sms.schoolmanagementsystem.services.IStudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Transactional
public class StudentServiceImpl implements IStudentService {

    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final CourseRepository courseRepository;
    private final AddressRepository addressRepository;

    public StudentServiceImpl(StudentRepository studentRepository, SchoolRepository schoolRepository, CourseRepository courseRepository, AddressRepository addressRepository) {
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
        this.courseRepository = courseRepository;
        this.addressRepository = addressRepository;
    }
    

    @Override
    public StudentDto saveStudent(StudentUpdateDto studentUpdateDto) {
        Student student = uDtoToEntity(studentUpdateDto);
        Student savedStudent  = studentRepository.save(student);
        return entityToDto(savedStudent);
    }

    @Override
    public void deleteStudent(long id){
        Optional<Student> optionalStudent=studentRepository.findById(id);
        optionalStudent.ifPresent(studentRepository::delete);
    }


    @Override
    public List<StudentDto> getAllStudents(){
        List<StudentDto> studentDtos = new ArrayList<>();
        for(Student student : studentRepository.findAll()) {
            StudentDto studentDto = entityToDto(student);
            studentDtos.add(studentDto);
        }
        return studentDtos;
    }

    @Override
    public StudentDto getStudentById(long id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            return entityToDto(student);
        }
        return null;
    }

    @Override
    public StudentDto updateStudent(long id, StudentUpdateDto studentUpdateDto) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = uDtoToEntity(studentUpdateDto);
            student.setId(id);
            Student updatedStudent = studentRepository.save(student);
            return entityToDto(updatedStudent);
        }
        return null;
    }

    public Student uDtoToEntity(StudentUpdateDto studentUpdateDto) {
        Student student = new Student();
        BeanUtils.copyProperties(studentUpdateDto, student);
        student.setSchool(schoolRepository.findById(studentUpdateDto.getSchoolId()).orElseThrow(() -> new SchoolNotFoundException("Student", studentUpdateDto.getSchoolId())));
        student.setAddress(addressRepository.findById(studentUpdateDto.getAddressId()).orElseThrow(()-> new AddressNotFoundException("Student", studentUpdateDto.getAddressId())));
        Set<Course> courses = new HashSet<>();
        if(studentUpdateDto.getCourseIds() != null) {
            for (Integer courseId : studentUpdateDto.getCourseIds()) {
                Optional<Course> courseOptional = courseRepository.findById(courseId);
                if (courseOptional.isPresent()) {
                    Course tempCourse = courseOptional.get();
                    tempCourse.getStudents().add(student);
                    courses.add(tempCourse);
                }
            }
        }
        student.setCourses(courses);
        return student;
    }
    public StudentDto entityToDto(Student student) {
        StudentDto studentDto = new StudentDto();
        BeanUtils.copyProperties(student, studentDto);
        return studentDto;
    }
}





