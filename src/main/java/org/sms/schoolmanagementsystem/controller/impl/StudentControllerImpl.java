package org.sms.schoolmanagementsystem.controller.impl;

import jakarta.validation.Valid;
import org.sms.schoolmanagementsystem.controller.IStudentController;
import org.sms.schoolmanagementsystem.dto.StudentDto;
import org.sms.schoolmanagementsystem.dto.StudentUpdateDto;
import org.sms.schoolmanagementsystem.services.IStudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
public class StudentControllerImpl implements IStudentController {

    private final IStudentService studentService;

    public StudentControllerImpl(IStudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/save")
    @Override
    public StudentDto saveStudent(@RequestBody @Valid StudentUpdateDto studentUpdateDto) {
        return studentService.saveStudent(studentUpdateDto);
    }
    @DeleteMapping("/delete/{id}")
    @Override
    public void deleteStudent(@PathVariable (name="id") long id){
        studentService.deleteStudent(id);
    }

    @GetMapping("/list")
    @Override
    public List<StudentDto> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/list/{id}")
    @Override
    public StudentDto getStudentById(@PathVariable(name = "id") long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/update/{id}")
    @Override
    public StudentDto updateStudent(@PathVariable long id, @RequestBody @Valid StudentUpdateDto studentUpdateDto) {
        return studentService.updateStudent(id, studentUpdateDto);
    }
}