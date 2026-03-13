package org.sms.schoolmanagementsystem.controller.impl;

import jakarta.validation.Valid;
import org.sms.schoolmanagementsystem.controller.ISchoolController;
import org.sms.schoolmanagementsystem.dto.LecturerUpdateDto;
import org.sms.schoolmanagementsystem.dto.SchoolDto;
import org.sms.schoolmanagementsystem.dto.SchoolUpdateDto;
import org.sms.schoolmanagementsystem.services.ISchoolService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/school")
public class SchoolControllerImpl implements ISchoolController {
    private final ISchoolService schoolService;
    public SchoolControllerImpl(ISchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/save")
    @Override
    public SchoolDto save(@RequestBody @Valid SchoolUpdateDto schoolUpdateDto) {
        return schoolService.save(schoolUpdateDto);
    }
    @DeleteMapping("/delete/{id}")
    @Override
    public void delete(@PathVariable(name = "id") long id) {
        schoolService.delete(id);
    }
    @Override
    @GetMapping("/update/{id}")
    public SchoolDto update(@PathVariable(name = "id") long id, @RequestBody @Valid SchoolUpdateDto schoolUpdateDto) {
        return schoolService.update(id, schoolUpdateDto);
    }
    @GetMapping("/list/{id}")
    @Override
    public SchoolDto getById(@PathVariable(name = "id") long id) {
        return schoolService.getById(id);
    }
    @GetMapping("/list")
    @Override
    public java.util.List<SchoolDto> getAll() {
        return schoolService.getAll();
    }
}
