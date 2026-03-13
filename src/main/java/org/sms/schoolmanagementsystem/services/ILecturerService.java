package org.sms.schoolmanagementsystem.services;

import org.sms.schoolmanagementsystem.dto.LecturerDto;
import org.sms.schoolmanagementsystem.dto.LecturerUpdateDto;

import java.util.List;

public interface ILecturerService {
    public LecturerDto employLecturer(LecturerUpdateDto lecturerUpdateDto);
    public void dismissLecturer(long id);
    public List<LecturerDto> getAllLecturers();
    public LecturerDto getLecturerById(long id);
    public LecturerDto updateLecturer(long id, LecturerUpdateDto lecturerUpdateDto);
}
