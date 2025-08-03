package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
        return Optional.ofNullable(facultyRepository.findById(facultyId).orElse(null));
    }

    public void dellFaculty(Long facultyId) {
        facultyRepository.deleteById(facultyId);
    }

    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public List<Faculty> findByColorNameContainsIgnoreCase(String str) {
        List<Faculty> faculties;
        if (!facultyRepository.findByNameContainsIgnoreCase(str).isEmpty()) {
            faculties = facultyRepository.findByNameContainsIgnoreCase(str);
        } else if (!facultyRepository.findByColorContainsIgnoreCase(str).isEmpty()) {
            faculties = facultyRepository.findByColorContainsIgnoreCase(str);
        } else {
            faculties = new ArrayList<>();
        }
        return faculties;
    }

    public List<Student> getStudentsOfFaculty(Long idFaculty) {
        Faculty faculty = Optional.ofNullable(facultyRepository.findById(idFaculty).orElse(null)).orElse(null);
        if (faculty == null) {
            return null;
        }
        return faculty.getStudents();
    }
}
