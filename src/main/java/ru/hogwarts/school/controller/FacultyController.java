package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("{facultyId}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long facultyId) {
        Faculty faculty = facultyService.getFacultyById(facultyId);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("{facultyId}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long facultyId) {
        facultyService.dellFaculty(facultyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("search/{color}")
    public ResponseEntity<List<Faculty>> searchColor(@RequestParam String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColorContainsIgnoreCase(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("getStudentsOfFaculty/{nameFaculty}")
    public ResponseEntity<List<String>> getStudentsOfFaculty(@PathVariable String nameFaculty) {
        if (nameFaculty == null || nameFaculty.isBlank()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(facultyService.getStudentsOfFaculty(nameFaculty));
    }
}
