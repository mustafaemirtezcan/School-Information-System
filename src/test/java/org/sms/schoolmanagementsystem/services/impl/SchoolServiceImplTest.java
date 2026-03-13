package org.sms.schoolmanagementsystem.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sms.schoolmanagementsystem.dto.SchoolDto;
import org.sms.schoolmanagementsystem.dto.SchoolUpdateDto;
import org.sms.schoolmanagementsystem.entity.School;
import org.sms.schoolmanagementsystem.entity.Address;
import org.sms.schoolmanagementsystem.exception.EntityNotFoundException.AddressNotFoundException;
import org.sms.schoolmanagementsystem.repository.AddressRepository;
import org.sms.schoolmanagementsystem.repository.SchoolRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchoolServiceImplTest {

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private SchoolServiceImpl schoolService;


    @Test
    void testSaveSchool() {
        int addressId = 1;

        SchoolUpdateDto schoolUpdateDto = new SchoolUpdateDto(
                "Test School",
                addressId
        );

        Address address = new Address();
        address.setId(addressId);
        address.setCity("Ankara");

        School savedSchoolEntity = new School();
        savedSchoolEntity.setId(10L);
        savedSchoolEntity.setName("Test School");
        savedSchoolEntity.setAddress(address);

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(schoolRepository.save(any(School.class))).thenReturn(savedSchoolEntity);

        SchoolDto schoolDto = schoolService.save(schoolUpdateDto);

        assertNotNull(schoolDto);
        assertEquals("Test School", schoolDto.getName());
        assertEquals(addressId, schoolDto.getAddress().getId());

        verify(addressRepository, times(1)).findById(addressId);
        verify(schoolRepository, times(1)).save(any(School.class));
    }

    @Test
    void testAddressNotFoundExceptionThrown() {
        int invalidAddressId = 999;
        SchoolUpdateDto schoolUpdateDto = new SchoolUpdateDto(
                "Test School",
                invalidAddressId
        );

        when(addressRepository.findById(invalidAddressId)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> schoolService.save(schoolUpdateDto));

        verify(addressRepository, times(1)).findById(invalidAddressId);
        verify(schoolRepository, never()).save(any(School.class));
    }

    @Test
    void testDeleteSchool_WhenExists() {
        long id = 5L;
        School school = new School();
        school.setId(id);

        when(schoolRepository.findById(id)).thenReturn(Optional.of(school));
        doNothing().when(schoolRepository).delete(school);

        schoolService.delete(id);

        verify(schoolRepository, times(1)).findById(id);
        verify(schoolRepository, times(1)).delete(school);
    }

    @Test
    void testDeleteSchool_WhenNotExists() {
        long id = 99L;
        when(schoolRepository.findById(id)).thenReturn(Optional.empty());

        schoolService.delete(id);

        verify(schoolRepository, times(1)).findById(id);
        verify(schoolRepository, never()).delete(any(School.class));
    }

    @Test
    void testUpdateSchool_WhenExists() {
        long id = 3L;
        int addressId = 1;

        Address address = new Address();
        address.setId(addressId);

        School existing = new School();
        existing.setId(id);
        existing.setName("Old School");
        existing.setAddress(address);

        SchoolUpdateDto updatedSchool = new SchoolUpdateDto(
                "Updated Test School",
                addressId
        );

        School updatedEntity = new School();
        updatedEntity.setId(id);
        updatedEntity.setName("Updated Test School");
        updatedEntity.setAddress(address);

        when(schoolRepository.findById(id)).thenReturn(Optional.of(existing));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(schoolRepository.save(any(School.class))).thenReturn(updatedEntity);

        SchoolDto updatedSchoolDto = schoolService.update(id, updatedSchool);

        assertNotNull(updatedSchoolDto);
        assertEquals("Updated Test School", updatedSchoolDto.getName());
        assertEquals(addressId, updatedSchoolDto.getAddress().getId());

        verify(schoolRepository, times(1)).findById(id);
        verify(addressRepository, times(1)).findById(addressId);
        verify(schoolRepository, times(1)).save(any(School.class));
    }

    @Test
    void testGetSchoolById_WhenExists() {
        long id = 1L;
        Address address = new Address();
        address.setId(1);
        address.setCity("Ankara");

        School school = new School();
        school.setId(id);
        school.setName("Test School");
        school.setAddress(address);

        when(schoolRepository.findById(id)).thenReturn(Optional.of(school));

        SchoolDto responseSchoolDto = schoolService.getById(id);

        assertNotNull(responseSchoolDto);
        assertEquals("Test School", responseSchoolDto.getName());
        assertEquals(1, responseSchoolDto.getAddress().getId());
        assertEquals("Ankara", responseSchoolDto.getAddress().getCity());

        verify(schoolRepository, times(1)).findById(id);
    }

    @Test
    void testGetSchoolById_WhenNotExists() {
        long id = 100L;
        when(schoolRepository.findById(id)).thenReturn(Optional.empty());

        SchoolDto responseSchoolDto = schoolService.getById(id);

        assertNull(responseSchoolDto);
        verify(schoolRepository, times(1)).findById(id);
    }

    @Test
    void testGetAllSchools() {
        Address address1 = new Address();
        address1.setId(1);
        address1.setCity("Ankara");

        Address address2 = new Address();
        address2.setId(2);
        address2.setCity("Istanbul");

        School school1 = new School();
        school1.setId(1L);
        school1.setName("Test School");
        school1.setAddress(address1);

        School school2 = new School();
        school2.setId(2L);
        school2.setName("Other Test School");
        school2.setAddress(address2);

        when(schoolRepository.findAll()).thenReturn(Arrays.asList(school1, school2));

        List<SchoolDto> schools = schoolService.getAll();

        assertEquals(2, schools.size());

        boolean foundFirst = false;
        boolean foundSecond = false;
        for (SchoolDto schoolDto : schools) {
            if (schoolDto.getName().equals("Test School")) {
                foundFirst = true;
            }
            if (schoolDto.getName().equals("Other Test School")) {
                foundSecond = true;
            }
        }

        assertTrue(foundFirst);
        assertTrue(foundSecond);

        verify(schoolRepository, times(1)).findAll();
    }
    @Test
    void testUDtoToEntity() {
        int addressId = 1;
        Address address = new Address();
        address.setId(addressId);
        address.setCity("Ankara");

        SchoolUpdateDto schoolUpdateDto = new SchoolUpdateDto(
                "Test School",
                addressId
        );

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));

        School school = schoolService.uDtoToEntity(schoolUpdateDto);

        assertEquals("Test School", school.getName());
        assertEquals(addressId, school.getAddress().getId());

        verify(addressRepository, times(1)).findById(addressId);
    }
    @Test
    void testEntityToDto() {
        Address address = new Address();
        address.setId(1);

        School school = new School();
        school.setId(1L);
        school.setName("Test School");
        school.setAddress(address);

        SchoolDto schoolDto = schoolService.entityToDto(school);

        assertEquals("Test School", schoolDto.getName());
        assertEquals(1, schoolDto.getAddress().getId());
    }
}