package org.sms.schoolmanagementsystem.services;

import org.sms.schoolmanagementsystem.dto.StudentDto;
import org.sms.schoolmanagementsystem.dto.StudentUpdateDto;

import java.util.List;

public interface IStudentService {
    public StudentDto saveStudent(StudentUpdateDto studentUpdateDto);
    public void deleteStudent(long studentId);
    public List<StudentDto> getAllStudents();
    public StudentDto getStudentById(long id);
    public StudentDto updateStudent(long id, StudentUpdateDto studentUpdateDto);
}
