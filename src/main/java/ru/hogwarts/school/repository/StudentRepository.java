package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findAllByAgeBetween(int minAge, int maxAge);

    @Query(value = "SELECT count(*) from student", nativeQuery = true)
    Integer getCountOfStudents();

    @Query(value = "SELECT avg(age) from student", nativeQuery = true)
    Double getAvgAgeOfStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> findLastFiveStudents();

}
