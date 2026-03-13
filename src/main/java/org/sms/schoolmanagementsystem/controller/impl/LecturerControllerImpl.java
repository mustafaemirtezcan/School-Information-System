package org.sms.schoolmanagementsystem.controller.impl;

import jakarta.validation.Valid;
import org.sms.schoolmanagementsystem.controller.ILecturerController;
import org.sms.schoolmanagementsystem.dto.LecturerDto;
import org.sms.schoolmanagementsystem.dto.LecturerUpdateDto;
import org.sms.schoolmanagementsystem.services.ILecturerService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/lecturer")
public class LecturerControllerImpl implements ILecturerController {

    private final ILecturerService lecturerService;

    public LecturerControllerImpl(ILecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @PostMapping("/employ")
    @Override
    public LecturerDto employLecturer(@RequestBody @Valid LecturerUpdateDto lecturerUpdateDto) {
        return lecturerService.employLecturer(lecturerUpdateDto);
    }
    @DeleteMapping("/dismiss/{id}")
    @Override
    public void dismissLecturer(@PathVariable(name = "id") long id) {
        lecturerService.dismissLecturer(id);
    }
    @PutMapping("/update/{id}")
    @Override
    public LecturerDto updateLecturer(@PathVariable long id, @RequestBody @Valid LecturerUpdateDto lecturerUpdateDto) {
        return lecturerService.updateLecturer(id, lecturerUpdateDto);
    }
    @GetMapping("/list")
    @Override
    public List<LecturerDto> getAllLecturers() {
        return lecturerService.getAllLecturers();
    }
    @GetMapping("/list/{id}")
    @Override
    public LecturerDto getLecturerById(@PathVariable(name = "id") long id) {
        return lecturerService.getLecturerById(id);
    }
}
