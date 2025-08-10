package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public Faculty getFacultyById(Long facultyId) {
        return facultyRepository.findById(facultyId).orElse(null);
    }

    public void dellFaculty(Long facultyId) {
        facultyRepository.deleteById(facultyId);
    }

    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(String str) {
        return facultyRepository.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(str, str);
    }

    public List<Student> getStudentsOfFaculty(Long idFaculty) {
        Faculty faculty = facultyRepository.findById(idFaculty).orElse(null);
        if (faculty == null) {
            return null;
        }
        return faculty.getStudents();
    }
}
