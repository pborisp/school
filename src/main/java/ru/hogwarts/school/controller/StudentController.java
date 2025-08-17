package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updateStudent = studentService.updateStudent(student);
        if (updateStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateStudent);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable Long studentId) {
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{studentId}/getFaculty")
    public ResponseEntity<FacultyDTO> getFaculty(@PathVariable Long studentId) {
        FacultyDTO facultyDTO = studentService.getFacultyById(studentId);
        if (facultyDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<Collection<Student>> getStudentAge(@RequestParam Integer minAge,
                                                             @RequestParam Integer maxAge) {
        if (maxAge > minAge) {
            return ResponseEntity.ok(studentService.findAllByAgeBetween(minAge, maxAge));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getCountOfStudents")
    public ResponseEntity<Integer> getCountOfStudents() {
        return ResponseEntity.ok(studentService.getCountOfStudents());
    }

    @GetMapping("/getAvgAgeOfStudents")
    public ResponseEntity<Double> getAvgAgeOfStudents() {
        return ResponseEntity.ok(studentService.getAvgAgeOfStudents());
    }

    @GetMapping("/getLastFiveStudents")
    public ResponseEntity<Collection<Student>> getLastFiveStudents() {
        return ResponseEntity.ok(studentService.getLastFiveStudents());
    }

}

