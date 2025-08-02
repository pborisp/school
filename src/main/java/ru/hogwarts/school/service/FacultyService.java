package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> getFacultyById(Long facultyId) {
        return Optional.ofNullable(facultyRepository.findById(facultyId).get());
    }

    public void dellFaculty(Long facultyId) {
        facultyRepository.deleteById(facultyId);
    }

    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public List<Faculty> findByColorOrNameContainsIgnoreCase(String color, String name) {
        if (name == null) {
            return facultyRepository.findByColorContainsIgnoreCase(color);
        }
        return facultyRepository.findByNameContainsIgnoreCase(name);
    }

    public Optional<List<Student>> getStudentsOfFaculty(Long idFaculty) {
        Faculty faculty = facultyRepository.findById(idFaculty).get();
        List<Student> students = faculty.getStudents();
        return Optional.ofNullable(students);
    }
}
