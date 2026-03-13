package org.sms.schoolmanagementsystem.services.impl;
import org.sms.schoolmanagementsystem.dto.SchoolDto;
import org.sms.schoolmanagementsystem.dto.SchoolUpdateDto;
import org.sms.schoolmanagementsystem.entity.Course;
import org.sms.schoolmanagementsystem.entity.Lecturer;
import org.sms.schoolmanagementsystem.entity.School;
import org.sms.schoolmanagementsystem.entity.Student;
import org.sms.schoolmanagementsystem.exception.EntityNotFoundException.AddressNotFoundException;
import org.sms.schoolmanagementsystem.repository.*;
import org.sms.schoolmanagementsystem.services.ISchoolService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SchoolServiceImpl implements ISchoolService {
    private final SchoolRepository schoolRepository;
    private final AddressRepository addressRepository;

    public SchoolServiceImpl(SchoolRepository schoolRepository, AddressRepository addressRepository) {
        this.schoolRepository = schoolRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public SchoolDto save(SchoolUpdateDto schoolUpdateDto) {
        School school = uDtoToEntity(schoolUpdateDto);
        School savedSchool = schoolRepository.save(school);
        return entityToDto(savedSchool);
    }

    @Override
    public void delete(long id) {
        Optional<School> optionalSchool = schoolRepository.findById(id);
        optionalSchool.ifPresent(schoolRepository::delete);
    }

    @Override
    public SchoolDto update(long id, SchoolUpdateDto schoolUpdateDto) {
        Optional<School> optionalSchool = schoolRepository.findById(id);
        if (optionalSchool.isPresent()) {
            School school = uDtoToEntity(schoolUpdateDto);
            school.setId(id);
            School updatedSchool = schoolRepository.save(school);
            return entityToDto(updatedSchool);
        }else {
            throw new RuntimeException("School with id " + id + " not found.");
        }
    }

    @Override
    public SchoolDto getById(long id) {
        Optional<School> optionalSchool = schoolRepository.findById(id);
        if (optionalSchool.isPresent()) {
            School school = optionalSchool.get();
            return entityToDto(school);
        }
        return null;
    }

    @Override
    public List<SchoolDto> getAll() {
        List<SchoolDto> schoolDtos = new ArrayList<>();
        for (School school : schoolRepository.findAll()) {
            SchoolDto schoolDto = entityToDto(school);
            schoolDtos.add(schoolDto);
        }
        return schoolDtos;
    }


    public School uDtoToEntity(SchoolUpdateDto schoolUpdateDto) {
        School school = new School();
        BeanUtils.copyProperties(schoolUpdateDto, school);
        school.setAddress(addressRepository.findById(schoolUpdateDto.getAddressId()).orElseThrow(()-> new AddressNotFoundException("School", schoolUpdateDto.getAddressId())));
        return school;
    }
    public SchoolDto entityToDto(School school) {
        SchoolDto schoolDto = new SchoolDto();
        BeanUtils.copyProperties(school, schoolDto);
        return schoolDto;
    }
}
