package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAgeLike(int age);

    List<Student> findByAgeBetween(int minAge, int maxAge);

    @Query(value = "SELECT f.name FROM student as s, faculty as f where s.faculty_id = f.id and s.id = :studentId", nativeQuery = true)
    String getFaculty(@Param("studentId") Long studentId);
}
