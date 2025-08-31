package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    private final static Logger LOGGER = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        LOGGER.info("Was invoked method for create faculty: {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(Faculty faculty) {
        LOGGER.info("Was invoked method for update faculty: {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long facultyId) {
        LOGGER.info("Was invoked method for get faculty by id: {}", facultyId);
        return facultyRepository.findById(facultyId).orElse(null);
    }

    public void dellFaculty(Long facultyId) {
        LOGGER.info("Was invoked method for delete faculty: {}", facultyId);
        facultyRepository.deleteById(facultyId);
    }

    public Collection<Faculty> getAllFaculty() {
        LOGGER.info("Was invoked method for get all faculty");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(String str) {
        LOGGER.info("Was invoked method for findByColorContainsIgnoreCaseOrNameContainsIgnoreCase: {}", str);
        return facultyRepository.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(str, str);
    }

    public List<Student> getStudentsOfFaculty(Long idFaculty) {
        LOGGER.info("Was invoked method for get students of faculty: {}", idFaculty);
        Faculty faculty = facultyRepository.findById(idFaculty).orElse(null);
        if (faculty == null) {
            return null;
        }
        return faculty.getStudents();
    }

    public String getMaxNameOfFaculty() {
        LOGGER.info("Was invoked method for get maxLength name of faculty");
        List<Faculty> allFaculties = facultyRepository.findAll();
        String maxNameFaculty = allFaculties.stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .get();
        return maxNameFaculty;
    }
}
