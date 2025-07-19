package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private Long count = 0L;

    public Student createStudent(Student student) {
        student.setId(++count);
        students.put(count, student);
        return student;
    }

    public Student getStudent(Long studentId) {
        return students.get(studentId);
    }

    public Student updateStudent(Long studentId, Student student) {
        students.put(studentId, student);
        return student;
    }

    public Student deleteStudent(Long studentId) {
        return students.remove(studentId);
    }

    public Collection<Student> getAllStudent() {
        return students.values();
    }

    public List<Student> searchAge(Integer age) {
        return students.values().stream()
                .filter(student -> student.getAge() == age).toList();
    }
}
