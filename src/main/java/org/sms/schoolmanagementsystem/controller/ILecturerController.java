package org.sms.schoolmanagementsystem.controller;

import org.sms.schoolmanagementsystem.dto.LecturerDto;
import org.sms.schoolmanagementsystem.dto.LecturerUpdateDto;
import org.sms.schoolmanagementsystem.dto.StudentDto;
import org.sms.schoolmanagementsystem.dto.StudentUpdateDto;

import java.util.List;

public interface ILecturerController {

    public LecturerDto employLecturer(LecturerUpdateDto lecturerUpdateDto);
    public void dismissLecturer(long id);
    public List<LecturerDto> getAllLecturers();
    public LecturerDto getLecturerById(long id);
    public LecturerDto updateLecturer(long id, LecturerUpdateDto lecturerUpdateDto);
}
