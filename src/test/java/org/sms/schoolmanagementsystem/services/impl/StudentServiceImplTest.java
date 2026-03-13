package org.sms.schoolmanagementsystem.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sms.schoolmanagementsystem.dto.StudentDto;
import org.sms.schoolmanagementsystem.dto.StudentUpdateDto;
import org.sms.schoolmanagementsystem.entity.Course;
import org.sms.schoolmanagementsystem.entity.Student;
import org.sms.schoolmanagementsystem.entity.School;
import org.sms.schoolmanagementsystem.entity.Address;
import org.sms.schoolmanagementsystem.repository.AddressRepository;
import org.sms.schoolmanagementsystem.repository.CourseRepository;
import org.sms.schoolmanagementsystem.repository.SchoolRepository;
import org.sms.schoolmanagementsystem.repository.StudentRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private StudentServiceImpl studentServiceImpl;



    @Test
    void testSaveStudent_WithoutCourses() {
        long schoolId = 1L;
        int addressId = 10;

        StudentUpdateDto studentUpdateDto = new StudentUpdateDto(
                "Test", "Student", "test@test.com", "1999-01-01",
                "1234567890", "S12345", 3.5, 30, "password", addressId, schoolId, null
        );

        School school = new School();
        school.setId(schoolId);
        school.setName("Test School");

        Address address = new Address();
        address.setId(addressId);
        address.setCity("Ankara");

        Student savedStudentEntity = new Student();
        savedStudentEntity.setId(5L);
        savedStudentEntity.setFirstName("Test");
        savedStudentEntity.setLastName("Student");
        savedStudentEntity.setEmail("test@test.com");
        savedStudentEntity.setBirthDate("1999-01-01");
        savedStudentEntity.setPhoneNumber("1234567890");
        savedStudentEntity.setStudentNumber("S12345");
        savedStudentEntity.setGpa(3.5);
        savedStudentEntity.setAchievedCredits(30);
        savedStudentEntity.setSchool(school);
        savedStudentEntity.setAddress(address);
        savedStudentEntity.setCourses(new HashSet<>());

        when(schoolRepository.findById(schoolId)).thenReturn(Optional.of(school));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudentEntity);

        StudentDto savedStudent = studentServiceImpl.saveStudent(studentUpdateDto);

        assertNotNull(savedStudent);
        assertEquals(5L, savedStudent.getId());
        assertEquals("Test", savedStudent.getFirstName());
        assertEquals("Student", savedStudent.getLastName());
        assertEquals("test@test.com", savedStudent.getEmail());
        assertEquals("1999-01-01", savedStudent.getBirthDate());
        assertEquals("1234567890", savedStudent.getPhoneNumber());
        assertEquals("S12345", savedStudent.getStudentNumber());
        assertEquals(3.5, savedStudent.getGpa());
        assertEquals(30, savedStudent.getAchievedCredits());
        assertEquals("Ankara", savedStudent.getAddress().getCity());
        assertEquals("Test School", savedStudent.getSchool().getName());
        assertTrue(savedStudent.getCourses().isEmpty());

        verify(schoolRepository, times(1)).findById(schoolId);
        verify(addressRepository, times(1)).findById(addressId);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testSaveStudent_WithCourses() {
        long schoolId = 1L;
        int addressId = 10;
        int courseId = 3;

        Set<Integer> courseIds = new HashSet<>();
        courseIds.add(courseId);

        StudentUpdateDto studentUpdateDto = new StudentUpdateDto(
                "New", "Student", "test@test.com", "1999-01-01",
                "1234567890", "S12345", 3.5, 30, "password", addressId, schoolId, courseIds
        );

        School school = new School();
        school.setId(schoolId);

        Address address = new Address();
        address.setId(addressId);

        Course course = new Course();
        course.setId(courseId);
        course.setName("Test Course");
        course.setCourseCode("MAT101");
        course.setStudents(new HashSet<>());

        Student savedStudentEntity = new Student();
        savedStudentEntity.setId(6L);
        savedStudentEntity.setFirstName("New");
        savedStudentEntity.setLastName("Student");
        savedStudentEntity.setSchool(school);
        savedStudentEntity.setAddress(address);
        savedStudentEntity.setCourses(new HashSet<>(Collections.singleton(course)));

        when(schoolRepository.findById(schoolId)).thenReturn(Optional.of(school));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudentEntity);

        StudentDto savedStudent = studentServiceImpl.saveStudent(studentUpdateDto);

        assertNotNull(savedStudent);
        assertEquals("New", savedStudent.getFirstName());
        assertEquals("Student", savedStudent.getLastName());
        assertEquals(1, savedStudent.getCourses().size());

        verify(schoolRepository, times(1)).findById(schoolId);
        verify(addressRepository, times(1)).findById(addressId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testDeleteStudent_WhenExists() {
        long id = 5L;
        Student student = new Student();
        student.setId(id);

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);

        studentServiceImpl.deleteStudent(id);

        verify(studentRepository, times(1)).findById(id);
        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    void testDeleteStudent_WhenNotExists() {
        long id = 99L;
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        studentServiceImpl.deleteStudent(id);

        verify(studentRepository, times(1)).findById(id);
        verify(studentRepository, never()).delete(any(Student.class));
    }
    @Test
    void testUpdateStudent_WhenExists() {
        long id = 4L;
        long schoolId = 1L;
        int addressId = 10;

        School school = new School();
        school.setId(schoolId);

        Address address = new Address();
        address.setId(addressId);

        Student existing = new Student();
        existing.setId(id);
        existing.setFirstName("Test");

        Set<Integer> courseIds = new HashSet<>();

        StudentUpdateDto studentUpdateDto2 = new StudentUpdateDto(
                "Updated Test", "Student", "test@test.com", "1999-01-01",
                "1234567890", "S12345", 3.5, 30, "password", addressId, schoolId, courseIds
        );

        Student updatedEntity = new Student();
        updatedEntity.setId(id);
        updatedEntity.setFirstName("Updated Test");
        updatedEntity.setSchool(school);
        updatedEntity.setAddress(address);

        when(studentRepository.findById(id)).thenReturn(Optional.of(existing));
        when(schoolRepository.findById(schoolId)).thenReturn(Optional.of(school));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedEntity);

        StudentDto updatedStudent = studentServiceImpl.updateStudent(id, studentUpdateDto2);

        assertNotNull(updatedStudent);
        assertEquals("Updated Test", updatedStudent.getFirstName());

        verify(studentRepository, times(1)).findById(id);
        verify(schoolRepository, times(1)).findById(schoolId);
        verify(addressRepository, times(1)).findById(addressId);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testGetAllStudents() {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setFirstName("Student1");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setFirstName("Student2");

        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        List<StudentDto> students = studentServiceImpl.getAllStudents();

        assertEquals(2, students.size());
        for (StudentDto studentDto : students) {
            assertTrue(studentDto.getFirstName().equals("Student1") || studentDto.getFirstName().equals("Student2"));
        }

        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testGetStudentById_WhenExists() {
        long id = 3L;
        Student student = new Student();
        student.setId(id);
        student.setFirstName("Test");

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        StudentDto foundStudent = studentServiceImpl.getStudentById(id);

        assertNotNull(foundStudent);
        assertEquals("Test", foundStudent.getFirstName());

        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    void testGetStudentById_WhenNotExists() {
        long id = 100L;
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        StudentDto foundStudent = studentServiceImpl.getStudentById(id);

        assertNull(foundStudent);
        verify(studentRepository, times(1)).findById(id);
    }


    @Test
    void testUDtoToEntity() {
        long schoolId = 1L;
        int addressId = 10;
        int courseId = 3;

        School school = new School();
        school.setId(schoolId);
        school.setName("Test School");

        Address address = new Address();
        address.setId(addressId);
        address.setCity("Ankara");

        Course course = new Course();
        course.setId(courseId);
        course.setName("Test Course");
        course.setCourseCode("MAT101");
        course.setStudents(new HashSet<>());

        Set<Integer> courseIds = new HashSet<>();
        courseIds.add(courseId);

        StudentUpdateDto studentUpdateDto = new StudentUpdateDto(
                "Test", "Student", "test@test.com", "1999-01-01",
                "1234567890", "S12345", 3.5, 30, "password", addressId, schoolId, courseIds
        );

        when(schoolRepository.findById(schoolId)).thenReturn(Optional.of(school));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Student student = studentServiceImpl.uDtoToEntity(studentUpdateDto);

        assertNotNull(student);
        assertEquals("Test", student.getFirstName());
        assertEquals("password", student.getPassword());
        assertEquals("Ankara", student.getAddress().getCity());
        assertEquals("Test School", student.getSchool().getName());
        assertEquals(1, student.getCourses().size());
        for (Course c : student.getCourses()) {
            assertEquals("Test Course", c.getName());
            assertEquals("MAT101", c.getCourseCode());
        }

        verify(schoolRepository, times(1)).findById(schoolId);
        verify(addressRepository, times(1)).findById(addressId);
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void testEntityToDto() {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Test");

        StudentDto studentDto = studentServiceImpl.entityToDto(student);

        assertNotNull(studentDto);
        assertEquals("Test", studentDto.getFirstName());
    }
}