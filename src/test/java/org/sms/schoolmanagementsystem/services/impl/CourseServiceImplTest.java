package org.sms.schoolmanagementsystem.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sms.schoolmanagementsystem.dto.CourseDto;
import org.sms.schoolmanagementsystem.dto.CourseUpdateDto;
import org.sms.schoolmanagementsystem.entity.Course;
import org.sms.schoolmanagementsystem.entity.Lecturer;
import org.sms.schoolmanagementsystem.entity.Student;
import org.sms.schoolmanagementsystem.entity.School;
import org.sms.schoolmanagementsystem.repository.CourseRepository;
import org.sms.schoolmanagementsystem.repository.LecturerRepository;
import org.sms.schoolmanagementsystem.repository.SchoolRepository;
import org.sms.schoolmanagementsystem.repository.StudentRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private LecturerRepository lecturerRepository;

    @InjectMocks
    private CourseServiceImpl courseServiceImpl;

    @Test
    void testAddCourse() {
        long schoolId = 1L;
        CourseUpdateDto courseUpdateDto = new CourseUpdateDto("Test Course", "CS101", 3, schoolId, null, null);

        School school = new School();
        school.setId(schoolId);
        school.setName("Test School");

        Course savedCourseEntity = new Course();
        savedCourseEntity.setId(10);
        savedCourseEntity.setName("Test Course");
        savedCourseEntity.setCourseCode("CS101");
        savedCourseEntity.setCredits(3);
        savedCourseEntity.setSchool(school);
        savedCourseEntity.setStudents(new HashSet<>());
        savedCourseEntity.setLecturers(new HashSet<>());

        when(schoolRepository.findById(schoolId)).thenReturn(Optional.of(school));
        when(courseRepository.save(any(Course.class))).thenReturn(savedCourseEntity);

        CourseDto savedCourse = courseServiceImpl.addCourse(courseUpdateDto);

        assertNotNull(savedCourse);
        assertEquals(10, savedCourse.getId());
        assertEquals("Test Course", savedCourse.getName());
        assertEquals("CS101", savedCourse.getCourseCode());
        assertEquals(3, savedCourse.getCredits());
        assertEquals(schoolId, savedCourse.getSchool().getId());

        verify(schoolRepository, times(1)).findById(schoolId);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testEnrollStudentsToCourse() {
        int courseId = 1;

        Course course = new Course();
        course.setId(courseId);
        course.setStudents(new HashSet<>());

        Student student = new Student();
        student.setId(5L);
        student.setFirstName("Test");
        student.setStudentNumber("S12345");
        student.setCourses(new HashSet<>());

        List<Long> studentIds = Collections.singletonList(5L);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(studentRepository.findById(5L)).thenReturn(Optional.of(student));
        when(courseRepository.save(course)).thenReturn(course);

        CourseDto updatedCourse = courseServiceImpl.enrollStudentsToCourse(courseId, studentIds);

        assertNotNull(updatedCourse);
        assertEquals(1, updatedCourse.getStudents().size());
        Student enrolledStudent = updatedCourse.getStudents().iterator().next();
        assertEquals(5L, enrolledStudent.getId());
        assertEquals("Test", enrolledStudent.getFirstName());
        assertEquals("S12345", enrolledStudent.getStudentNumber());

        verify(courseRepository, times(1)).findById(courseId);
        verify(studentRepository, times(1)).findById(5L);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testEnrollLecturersToCourse() {
        int courseId = 1;

        Course course = new Course();
        course.setId(courseId);
        course.setLecturers(new HashSet<>());

        Lecturer lecturer = new Lecturer();
        lecturer.setId(7L);
        lecturer.setFirstName("Ali");
        lecturer.setLecturerNumber("L12345");
        lecturer.setCourses(new HashSet<>());

        List<Long> lecturerIds = Collections.singletonList(7L);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(lecturerRepository.findById(7L)).thenReturn(Optional.of(lecturer));
        when(courseRepository.save(course)).thenReturn(course);

        CourseDto updatedCourse = courseServiceImpl.enrollLecturersToCourse(courseId, lecturerIds);

        assertNotNull(updatedCourse);
        assertEquals(1, updatedCourse.getLecturers().size());
        Lecturer enrolledLecturer = updatedCourse.getLecturers().iterator().next();
        assertEquals(7L, enrolledLecturer.getId());
        assertEquals("Ali", enrolledLecturer.getFirstName());
        assertEquals("L12345", enrolledLecturer.getLecturerNumber());

        verify(courseRepository, times(1)).findById(courseId);
        verify(lecturerRepository, times(1)).findById(7L);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testDeleteCourse_WhenCourseExists() {
        int id = 2;
        Course course = new Course();
        course.setId(id);

        when(courseRepository.findById(id)).thenReturn(Optional.of(course));
        doNothing().when(courseRepository).delete(course);

        courseServiceImpl.deleteCourse(id);

        verify(courseRepository, times(1)).findById(id);
        verify(courseRepository, times(1)).delete(course);
    }

    @Test
    void testDeleteCourse_WhenCourseDoesNotExist() {
        int id = 99;
        when(courseRepository.findById(id)).thenReturn(Optional.empty());

        courseServiceImpl.deleteCourse(id);

        verify(courseRepository, times(1)).findById(id);
        verify(courseRepository, never()).delete(any(Course.class));
    }

    @Test
    void testGetAllCourses() {
        Course course1 = new Course();
        course1.setId(1);
        course1.setName("Test Course 1");

        Course course2 = new Course();
        course2.setId(2);
        course2.setName("Test Course 2");

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

        List<CourseDto> courses = courseServiceImpl.getAllCourses();

        assertEquals(2, courses.size());
        boolean foundCourse1 = false;
        boolean foundCourse2 = false;
        for (CourseDto course : courses) {
            if (course.getName().equals("Test Course 1")) {
                foundCourse1 = true;
            }
            if (course.getName().equals("Test Course 2")) {
                foundCourse2 = true;
            }
        }
        assertTrue(foundCourse1);
        assertTrue(foundCourse2);

        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testGetCourseById_WhenExists() {
        int id = 3;
        Course course = new Course();
        course.setId(id);
        course.setName("Test Course");
        course.setCourseCode("CS101");
        course.setCredits(3);

        when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        CourseDto foundCourse = courseServiceImpl.getCourseById(id);

        assertNotNull(foundCourse);
        assertEquals("Test Course", foundCourse.getName());
        assertEquals("CS101", foundCourse.getCourseCode());
        assertEquals(3, foundCourse.getCredits());

        verify(courseRepository, times(1)).findById(id);
    }

    @Test
    void testGetCourseById_WhenNotExists() {
        int id = 100;
        when(courseRepository.findById(id)).thenReturn(Optional.empty());

        CourseDto foundCourse = courseServiceImpl.getCourseById(id);

        assertNull(foundCourse);
        verify(courseRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateCourse_WhenExists() {
        int id = 4;
        long schoolId = 1L;

        School school = new School();
        school.setId(schoolId);

        Course existing = new Course();
        existing.setId(id);
        existing.setName("Old course");
        existing.setSchool(school);

        CourseUpdateDto updatedCourseDto = new CourseUpdateDto("Updated Course", "CS102", 4, schoolId, null, null);

        Course updatedEntity = new Course();
        updatedEntity.setId(id);
        updatedEntity.setName("Updated Course");
        updatedEntity.setCourseCode("CS102");
        updatedEntity.setCredits(4);
        updatedEntity.setSchool(school);

        when(courseRepository.findById(id)).thenReturn(Optional.of(existing));
        when(schoolRepository.findById(schoolId)).thenReturn(Optional.of(school));
        when(courseRepository.save(any(Course.class))).thenReturn(updatedEntity);

        CourseDto updatedCourse = courseServiceImpl.updateCourse(id, updatedCourseDto);

        assertNotNull(updatedCourse);
        assertEquals("Updated Course", updatedCourse.getName());
        assertEquals("CS102", updatedCourse.getCourseCode());
        assertEquals(4, updatedCourse.getCredits());
        assertEquals(schoolId, updatedCourse.getSchool().getId());

        verify(courseRepository, times(1)).findById(id);
        verify(schoolRepository, times(1)).findById(schoolId);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testUpdateCourse_WhenNotExists() {
        int id = 200;
        CourseUpdateDto updatedCourseDto = new CourseUpdateDto("Updated Course", "CS102", 4, 1L, null, null);

        when(courseRepository.findById(id)).thenReturn(Optional.empty());

        CourseDto updatedCourse = courseServiceImpl.updateCourse(id, updatedCourseDto);

        assertNull(updatedCourse);
        verify(courseRepository, times(1)).findById(id);
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void testUDtoToEntity() {
        long schoolId = 1L;
        School school = new School();
        school.setId(schoolId);

        CourseUpdateDto courseUpdateDto = new CourseUpdateDto("Test Course", "CS101", 3, schoolId, null, null);

        when(schoolRepository.findById(schoolId)).thenReturn(Optional.of(school));

        Course course = courseServiceImpl.uDtoToEntity(courseUpdateDto);

        assertEquals("Test Course", course.getName());
        assertEquals("CS101", course.getCourseCode());
        assertEquals(3, course.getCredits());
        assertNotNull(course.getSchool());
        assertEquals(schoolId, course.getSchool().getId());

        verify(schoolRepository, times(1)).findById(schoolId);
    }

    @Test
    void testEntityToDto() {
        Course course = new Course();
        course.setId(1);
        course.setName("Test Course");
        course.setCourseCode("CS101");
        course.setCredits(3);

        CourseDto courseDto = courseServiceImpl.entityToDto(course);

        assertEquals("Test Course", courseDto.getName());
        assertEquals("CS101", courseDto.getCourseCode());
        assertEquals(3, courseDto.getCredits());
    }
}