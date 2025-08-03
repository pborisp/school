package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

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

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updateFaculty = facultyService.updateFaculty(faculty);
        if (updateFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateFaculty);
    }

    @GetMapping("{facultyId}")
    public ResponseEntity<Optional<Faculty>> getFaculty(@PathVariable Long facultyId) {
        if (facultyService.getFacultyById(facultyId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyService.getFacultyById(facultyId));
    }

    @DeleteMapping("{facultyId}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long facultyId) {
        facultyService.dellFaculty(facultyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Faculty>> searchColorOrName(@RequestParam String str) {
        return ResponseEntity.ok(facultyService.findByColorNameContainsIgnoreCase(str));
    }

    @GetMapping("/{idFaculty}/getStudentsOfFaculty")
    public ResponseEntity<List<Student>> getStudentsOfFaculty(@PathVariable Long idFaculty) {
        if (facultyService.getStudentsOfFaculty(idFaculty) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyService.getStudentsOfFaculty(idFaculty));
    }
}
