package org.sms.schoolmanagementsystem.services.impl;

import org.sms.schoolmanagementsystem.dto.LecturerDto;
import org.sms.schoolmanagementsystem.dto.LecturerUpdateDto;
import org.sms.schoolmanagementsystem.entity.Course;
import org.sms.schoolmanagementsystem.entity.Lecturer;
import org.sms.schoolmanagementsystem.exception.EntityNotFoundException.AddressNotFoundException;
import org.sms.schoolmanagementsystem.exception.EntityNotFoundException.SchoolNotFoundException;
import org.sms.schoolmanagementsystem.repository.AddressRepository;
import org.sms.schoolmanagementsystem.repository.CourseRepository;
import org.sms.schoolmanagementsystem.repository.LecturerRepository;
import org.sms.schoolmanagementsystem.repository.SchoolRepository;
import org.sms.schoolmanagementsystem.services.ILecturerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LecturerServiceImpl implements ILecturerService {

    private final LecturerRepository lecturerRepository;
    private final SchoolRepository schoolRepository;
    private final CourseRepository courseRepository;
    private final AddressRepository addressRepository;

    public LecturerServiceImpl(LecturerRepository lecturerRepository, SchoolRepository schoolRepository, CourseRepository courseRepository, AddressRepository addressRepository)
     {
        this.lecturerRepository = lecturerRepository;
        this.schoolRepository = schoolRepository;
        this.courseRepository = courseRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public LecturerDto employLecturer(LecturerUpdateDto lecturerUpdateDto) {
        Lecturer lecturer = uDtoToEntity(lecturerUpdateDto);
        Lecturer savedLecturer = lecturerRepository.save(lecturer);
        return entityToDto(savedLecturer);
    }

    @Override
    public void dismissLecturer(long id) {
        Optional <Lecturer> optionalLecturer = lecturerRepository.findById(id);
        optionalLecturer.ifPresent(lecturerRepository::delete);
    }

    @Override
    public List<LecturerDto> getAllLecturers() {
        List<LecturerDto> lecturerDtos = new ArrayList<>();
        for (Lecturer lecturer : lecturerRepository.findAll()) {
            LecturerDto lecturerDto = entityToDto(lecturer);
            lecturerDtos.add(lecturerDto);
        }
        return lecturerDtos;
    }

    @Override
    public LecturerDto getLecturerById(long id) {
        Optional<Lecturer> optionalLecturer = lecturerRepository.findById(id);
        if (optionalLecturer.isPresent()) {
            Lecturer lecturer = optionalLecturer.get();
            return entityToDto(lecturer);
        }
        return null;
    }
    @Override
    public LecturerDto updateLecturer(long id, LecturerUpdateDto lecturerUpdateDto) {
        Optional<Lecturer> optionalLecturer = lecturerRepository.findById(id);
        if (optionalLecturer.isPresent()) {
            Lecturer lecturer = uDtoToEntity(lecturerUpdateDto);
            lecturer.setId(id);
            Lecturer updatedLecturer=lecturerRepository.save(lecturer);
            return entityToDto(updatedLecturer);
        }
        return null;
    }

    public Lecturer uDtoToEntity(LecturerUpdateDto lecturerUpdateDto) {
        Lecturer lecturer = new Lecturer();
        BeanUtils.copyProperties(lecturerUpdateDto, lecturer);
        lecturer.setSchool(schoolRepository.findById(lecturerUpdateDto.getSchoolId()).orElseThrow(() -> new SchoolNotFoundException("Lecturer", lecturerUpdateDto.getSchoolId())));
        lecturer.setAddress(addressRepository.findById(lecturerUpdateDto.getAddressId()).orElseThrow(()-> new AddressNotFoundException("Lecturer", lecturerUpdateDto.getAddressId())));
        Set<Course> courses = new HashSet<>();
        Set<Integer> courseIds = lecturerUpdateDto.getCourseIds();
        if (courseIds != null) {
            for (Integer courseId : courseIds) {
                Optional<Course> optionalCourse = courseRepository.findById(courseId);
                if(optionalCourse.isPresent()) {
                    Course tempCourse = optionalCourse.get();
                    tempCourse.getLecturers().add(lecturer);
                    courses.add(tempCourse);
                }
            }
        }
        lecturer.setCourses(courses);
        return lecturer;
    }

    public LecturerDto entityToDto(Lecturer lecturer) {
        LecturerDto lecturerDto = new LecturerDto();
        BeanUtils.copyProperties(lecturer, lecturerDto);
        return lecturerDto;
    }
}
