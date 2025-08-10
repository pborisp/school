package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    public FacultyDTO getFacultyById(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return null;
        }
        return FacultyDTO.fromfaculty(student.getFaculty());
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Collection<Student> findAllByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findAllByAgeBetween(minAge, maxAge);
    }

}
