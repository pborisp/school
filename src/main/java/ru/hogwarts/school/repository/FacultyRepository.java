package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColorContainsIgnoreCase(String color);

    @Query(value = "SELECT s.name FROM student as s, faculty as f where s.faculty_id = f.id and f.name = :nameFaculty", nativeQuery = true)
    List<String> getStudentsOfFaculty(@Param("nameFaculty") String nameFaculty);
}
