package org.sms.schoolmanagementsystem.controller;

import org.sms.schoolmanagementsystem.dto.StudentDto;
import org.sms.schoolmanagementsystem.dto.StudentUpdateDto;

import java.util.List;

public interface IStudentController {

    public StudentDto saveStudent(StudentUpdateDto studentUpdateDto);
    public void deleteStudent(long id);
    public List<StudentDto> getAllStudents();
    public StudentDto getStudentById(long id);
    public StudentDto updateStudent(long id, StudentUpdateDto studentUpdateDto);
}
