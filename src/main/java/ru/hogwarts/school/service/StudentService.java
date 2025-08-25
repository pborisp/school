package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    private final static Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        LOGGER.info("Was invoked method for create student: {}", student);
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        LOGGER.info("Was invoked method for update student: {}", student);
        return studentRepository.save(student);
    }

    public Student getStudentById(Long studentId) {
        LOGGER.debug("Was invoked method for get student by Id: {}", studentId);
        return studentRepository.findById(studentId).orElse(null);
    }

    public FacultyDTO getFacultyById(Long studentId) {
        LOGGER.debug("Was invoked method for get faculty by id: {}", studentId);
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return null;
        }
        return FacultyDTO.fromfaculty(student.getFaculty());
    }

    public void deleteStudent(Long studentId) {
        LOGGER.info("Was invoked method for delete student: {}", studentId);
        studentRepository.deleteById(studentId);
    }

    public Collection<Student> getAllStudent() {
        LOGGER.debug("Was invoked method for get all students");
        return studentRepository.findAll();
    }

    public Collection<Student> findAllByAgeBetween(int minAge, int maxAge) {
        LOGGER.info("Was invoked method for find all by between");
        return studentRepository.findAllByAgeBetween(minAge, maxAge);
    }

    public Integer getCountOfStudents() {
        LOGGER.info("Was invoked method for get count of student");
        return studentRepository.getCountOfStudents();
    }

    public Double getAvgAgeOfStudents() {
        LOGGER.info("Was invoked method for get avg age of students");
        return studentRepository.getAvgAgeOfStudents();
    }

    public Collection<Student> getLastFiveStudents() {
        LOGGER.info("Was invoked method for get last five students");
        return studentRepository.findLastFiveStudents();
    }
}
