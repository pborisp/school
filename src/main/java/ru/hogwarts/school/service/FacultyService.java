package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty: {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method for update faculty: {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long facultyId) {
        logger.info("Was invoked method for get faculty by id: {}", facultyId);
        return facultyRepository.findById(facultyId).orElse(null);
    }

    public void dellFaculty(Long facultyId) {
        logger.info("Was invoked method for delete faculty: {}", facultyId);
        facultyRepository.deleteById(facultyId);
    }

    public Collection<Faculty> getAllFaculty() {
        logger.info("Was invoked method for get all faculty");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(String str) {
        logger.info("Was invoked method for findByColorContainsIgnoreCaseOrNameContainsIgnoreCase: {}", str);
        return facultyRepository.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(str, str);
    }

    public List<Student> getStudentsOfFaculty(Long idFaculty) {
        logger.info("Was invoked method for get students of faculty: {}", idFaculty);
        Faculty faculty = facultyRepository.findById(idFaculty).orElse(null);
        if (faculty == null) {
            return null;
        }
        return faculty.getStudents();
    }
}
