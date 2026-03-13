package org.sms.schoolmanagementsystem.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sms.schoolmanagementsystem.dto.LecturerDto;
import org.sms.schoolmanagementsystem.dto.LecturerUpdateDto;
import org.sms.schoolmanagementsystem.entity.Course;
import org.sms.schoolmanagementsystem.entity.Lecturer;
import org.sms.schoolmanagementsystem.entity.School;
import org.sms.schoolmanagementsystem.entity.Address;
import org.sms.schoolmanagementsystem.repository.AddressRepository;
import org.sms.schoolmanagementsystem.repository.CourseRepository;
import org.sms.schoolmanagementsystem.repository.LecturerRepository;
import org.sms.schoolmanagementsystem.repository.SchoolRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LecturerServiceImplTest {

    @Mock
    private LecturerRepository lecturerRepository;

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private LecturerServiceImpl lecturerServiceImpl;

    @Test
    void testEmployLecturer_WithoutCourses() {
        long schoolId = 1L;
        int addressId = 10;

        LecturerUpdateDto lecturerUpdateDto = new LecturerUpdateDto(
                "Test", "Lecturer", "lecturer@lecturer.com", "L12345", "1234567890",
                "Math", "password", addressId, schoolId, null
        );

        School school = new School();
        school.setId(schoolId);
        school.setName("Test School");

        Address address = new Address();
        address.setId(addressId);
        address.setCity("Ankara");

        Lecturer savedLecturerEntity = new Lecturer();
        savedLecturerEntity.setId(5L);
        savedLecturerEntity.setFirstName("Test");
        savedLecturerEntity.setLastName("Lecturer");
        savedLecturerEntity.setEmail("lecturer@lecturer.com");
        savedLecturerEntity.setPhoneNumber("1234567890");
        savedLecturerEntity.setLecturerNumber("L12345");
        savedLecturerEntity.setBranch("Math");
        savedLecturerEntity.setSchool(school);
        savedLecturerEntity.setAddress(address);
        savedLecturerEntity.setCourses(new HashSet<>());

        when(schoolRepository.findById(schoolId)).thenReturn(Optional.of(school));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(lecturerRepository.save(any(Lecturer.class))).thenReturn(savedLecturerEntity);

        LecturerDto savedLecturer = lecturerServiceImpl.employLecturer(lecturerUpdateDto);

        assertNotNull(savedLecturer);
        assertEquals(5L, savedLecturer.getId());
        assertEquals("Test", savedLecturer.getFirstName());
        assertEquals("Lecturer", savedLecturer.getLastName());
        assertEquals("lecturer@lecturer.com", savedLecturer.getEmail());

        verify(schoolRepository, times(1)).findById(schoolId);
        verify(addressRepository, times(1)).findById(addressId);
        verify(lecturerRepository, times(1)).save(any(Lecturer.class));
    }

    @Test
    void testEmployLecturer_WithCourses() {
        long schoolId = 1L;
        int addressId = 10;
        int courseId = 3;

        Set<Integer> courseIds = new HashSet<>();
        courseIds.add(courseId);

        LecturerUpdateDto lecturerUpdateDto = new LecturerUpdateDto(
                "New Test", "Lecturer", "lecturer@lecturer.com", "L12345", "1234567890",
                "Math", "password", addressId, schoolId, courseIds
        );

        School school = new School();
        school.setId(schoolId);

        Address address = new Address();
        address.setId(addressId);

        Course course = new Course();
        course.setId(courseId);
        course.setLecturers(new HashSet<>());

        Lecturer savedLecturerEntity = new Lecturer();
        savedLecturerEntity.setId(6L);
        savedLecturerEntity.setFirstName("New Test");
        savedLecturerEntity.setLastName("Lecturer");
        savedLecturerEntity.setSchool(school);
        savedLecturerEntity.setAddress(address);
        savedLecturerEntity.setCourses(new HashSet<>(Collections.singleton(course)));

        when(schoolRepository.findById(schoolId)).thenReturn(Optional.of(school));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(lecturerRepository.save(any(Lecturer.class))).thenReturn(savedLecturerEntity);

        LecturerDto savedLecturer = lecturerServiceImpl.employLecturer(lecturerUpdateDto);

        assertNotNull(savedLecturer);
        assertEquals("New Test", savedLecturer.getFirstName());
        assertEquals("Lecturer", savedLecturer.getLastName());

        verify(schoolRepository, times(1)).findById(schoolId);
        verify(addressRepository, times(1)).findById(addressId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(lecturerRepository, times(1)).save(any(Lecturer.class));
    }

    @Test
    void testDismissLecturer_WhenExists() {
        long id = 5L;
        Lecturer lecturer = new Lecturer();
        lecturer.setId(id);

        when(lecturerRepository.findById(id)).thenReturn(Optional.of(lecturer));
        doNothing().when(lecturerRepository).delete(lecturer);

        lecturerServiceImpl.dismissLecturer(id);

        verify(lecturerRepository, times(1)).findById(id);
        verify(lecturerRepository, times(1)).delete(lecturer);
    }

    @Test
    void testDismissLecturer_WhenDoesNotExist() {
        long id = 99L;
        when(lecturerRepository.findById(id)).thenReturn(Optional.empty());

        lecturerServiceImpl.dismissLecturer(id);

        verify(lecturerRepository, times(1)).findById(id);
        verify(lecturerRepository, never()).delete(any(Lecturer.class));
    }

    @Test
    void testGetAllLecturers() {
        Lecturer lecturer1 = new Lecturer();
        lecturer1.setId(1L);
        lecturer1.setFirstName("Test1");

        Lecturer lecturer2 = new Lecturer();
        lecturer2.setId(2L);
        lecturer2.setFirstName("Test2");

        when(lecturerRepository.findAll()).thenReturn(Arrays.asList(lecturer1, lecturer2));

        List<LecturerDto> lecturers = lecturerServiceImpl.getAllLecturers();

        assertEquals(2, lecturers.size());
        for (LecturerDto lecturerDto : lecturers) {
            assertTrue(lecturerDto.getFirstName().equals("Test1") || lecturerDto.getFirstName().equals("Test2"));
        }

        verify(lecturerRepository, times(1)).findAll();
    }

    @Test
    void testGetLecturerById_WhenExists() {
        long id = 3L;
        Lecturer lecturer = new Lecturer();
        lecturer.setId(id);
        lecturer.setFirstName("Test");
        lecturer.setLastName("Lecturer");

        when(lecturerRepository.findById(id)).thenReturn(Optional.of(lecturer));

        LecturerDto foundLecturer = lecturerServiceImpl.getLecturerById(id);

        assertNotNull(foundLecturer);
        assertEquals("Test", foundLecturer.getFirstName());
        assertEquals("Lecturer", foundLecturer.getLastName());

        verify(lecturerRepository, times(1)).findById(id);
    }

    @Test
    void testGetLecturerById_WhenNotExists() {
        long id = 100L;
        when(lecturerRepository.findById(id)).thenReturn(Optional.empty());

        LecturerDto foundLecturer = lecturerServiceImpl.getLecturerById(id);

        assertNull(foundLecturer);
        verify(lecturerRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateLecturer_WhenExists() {
        long id = 4L;
        long schoolId = 1L;
        int addressId = 10;

        School school = new School();
        school.setId(schoolId);

        Address address = new Address();
        address.setId(addressId);

        Lecturer existing = new Lecturer();
        existing.setId(id);
        existing.setFirstName("Test");

        LecturerUpdateDto updatedLecturerDto = new LecturerUpdateDto(
                "Updated Test", "Lecturer", "lecturer@lecturer.com", "L12345", "1234567890",
                "Math", "password", addressId, schoolId, null
        );

        Lecturer updatedEntity = new Lecturer();
        updatedEntity.setId(id);
        updatedEntity.setFirstName("Updated Test");
        updatedEntity.setLastName("Lecturer");
        updatedEntity.setSchool(school);
        updatedEntity.setAddress(address);

        when(lecturerRepository.findById(id)).thenReturn(Optional.of(existing));
        when(schoolRepository.findById(schoolId)).thenReturn(Optional.of(school));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(lecturerRepository.save(any(Lecturer.class))).thenReturn(updatedEntity);

        LecturerDto updatedLecturer = lecturerServiceImpl.updateLecturer(id, updatedLecturerDto);

        assertNotNull(updatedLecturer);
        assertEquals("Updated Test", updatedLecturer.getFirstName());

        verify(lecturerRepository, times(1)).findById(id);
        verify(schoolRepository, times(1)).findById(schoolId);
        verify(addressRepository, times(1)).findById(addressId);
        verify(lecturerRepository, times(1)).save(any(Lecturer.class));
    }

    @Test
    void testUpdateLecturer_WhenNotExists() {
        long id = 200L;
        LecturerUpdateDto updatedLecturerDto = new LecturerUpdateDto(
                "Updated Test", "Lecturer", "lecturer@lecturer.com", "L12345", "1234567890",
                "Math", "password", 10, 1L, null
        );

        when(lecturerRepository.findById(id)).thenReturn(Optional.empty());

        LecturerDto updatedLecturer = lecturerServiceImpl.updateLecturer(id, updatedLecturerDto);

        assertNull(updatedLecturer);
        verify(lecturerRepository, times(1)).findById(id);
        verify(lecturerRepository, never()).save(any(Lecturer.class));
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
        course.setLecturers(new HashSet<>());

        Set<Integer> courseIds = new HashSet<>();
        courseIds.add(courseId);

        LecturerUpdateDto lecturerUpdateDto = new LecturerUpdateDto(
                "Test", "Lecturer", "lecturer@lecturer.com", "L12345", "1234567890",
                "Math", "password", addressId, schoolId, courseIds
        );

        when(schoolRepository.findById(schoolId)).thenReturn(Optional.of(school));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Lecturer lecturer = lecturerServiceImpl.uDtoToEntity(lecturerUpdateDto);

        assertNotNull(lecturer);
        assertEquals("Test", lecturer.getFirstName());
        assertEquals("Lecturer", lecturer.getLastName());
        assertEquals("Ankara", lecturer.getAddress().getCity());
        assertEquals("Test School", lecturer.getSchool().getName());
        assertEquals(1, lecturer.getCourses().size());
        for (Course c : lecturer.getCourses()) {
            assertEquals("Test Course", c.getName());
            assertEquals("MAT101", c.getCourseCode());
        }

        verify(schoolRepository, times(1)).findById(schoolId);
        verify(addressRepository, times(1)).findById(addressId);
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void testEntityToDto() {
        Lecturer lecturer = new Lecturer();
        lecturer.setId(1L);
        lecturer.setFirstName("Test");
        lecturer.setLastName("Lecturer");

        LecturerDto lecturerDto = lecturerServiceImpl.entityToDto(lecturer);

        assertNotNull(lecturerDto);
        assertEquals("Test", lecturerDto.getFirstName());
        assertEquals("Lecturer", lecturerDto.getLastName());
    }
}