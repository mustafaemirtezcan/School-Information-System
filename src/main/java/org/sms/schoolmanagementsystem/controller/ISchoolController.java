package org.sms.schoolmanagementsystem.controller;

import org.sms.schoolmanagementsystem.dto.SchoolDto;
import org.sms.schoolmanagementsystem.dto.SchoolUpdateDto;

import java.util.List;

public interface ISchoolController {
    SchoolDto save(SchoolUpdateDto schoolUpdateDto);
    void delete(long id);
    SchoolDto getById(long id);
    List<SchoolDto> getAll();
    SchoolDto update(long id,SchoolUpdateDto schoolUpdateDto);
}
