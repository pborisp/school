package ru.hogwarts.school.service;

import liquibase.sdk.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    public Collection<String> getNameStudentsA() {
        LOGGER.debug("Was invoked method for get all students from A");
        List<Student> allStudents = studentRepository.findAll();
        Collection<String> sortedStudents = allStudents.stream()
                .map(Student::getName)
                .filter(str -> str.startsWith("A") || str.startsWith("a"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
        return sortedStudents;
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

    public Double getMiddleAgeStudents() {
        LOGGER.debug("Was invoked method for get middle age of students");
        List<Student> allStudents = studentRepository.findAll();
        Double middleAgeOfStudents = allStudents.stream()
                .map(Student::getAge)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
        return middleAgeOfStudents;
    }

    public void getNameStudentsParallel() {
        LOGGER.debug("Was invoked method for get name of students - parallels");
        List<Student> allStudents = studentRepository.findAll();

        System.out.println("1й студент: " + allStudents.get(0).getName());
        System.out.println("2й студент: " + allStudents.get(1).getName());

        new Thread(() -> {
            System.out.println("3й студент: " + allStudents.get(2).getName());
            System.out.println("4й студент: " + allStudents.get(3).getName());
        }).start();

        new Thread(() -> {
            System.out.println("5й студент: " + allStudents.get(4).getName());
            System.out.println("6й студент: " + allStudents.get(5).getName());
        }).start();
    }

    public synchronized void getNameStudentsSynchronized() {
        LOGGER.debug("Was invoked method for get name of students - synchronized");
        List<Student> allStudents = studentRepository.findAll();

        System.out.println("1й студент: " + allStudents.get(0).getName());
        System.out.println("2й студент: " + allStudents.get(1).getName());

        new Thread(() -> {
            System.out.println("3й студент: " + allStudents.get(2).getName());
            System.out.println("4й студент: " + allStudents.get(3).getName());
            try {
                Thread.sleep(000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            System.out.println("5й студент: " + allStudents.get(4).getName());
            System.out.println("6й студент: " + allStudents.get(5).getName());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
