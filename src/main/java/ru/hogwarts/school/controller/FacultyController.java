package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updateFaculty);
    }

    @GetMapping("{facultyId}")
    public ResponseEntity<Optional<Faculty>> getFaculty(@PathVariable Long facultyId) {
        Optional<Faculty> faculty = facultyService.getFacultyById(facultyId);
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

    @GetMapping("/search")
    public ResponseEntity<List<Faculty>> searchColor(@RequestParam(required = false) String color,
                                                     @RequestParam(required = false) String name) {
        if (color == null && name == null) {
            ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(facultyService.findByColorOrNameContainsIgnoreCase(color, name));
    }

    @GetMapping("/{idFaculty}/getStudentsOfFaculty")
    public ResponseEntity<Optional<List<Student>>> getStudentsOfFaculty(@PathVariable Long idFaculty) {
        if (idFaculty == null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(facultyService.getStudentsOfFaculty(idFaculty));
    }
}
