package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long countFacultyId = 0L;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++countFacultyId);
        faculties.put(countFacultyId, faculty);
        return faculty;
    }

    public Faculty getFaculty(Long facultyId) {
        return faculties.get(facultyId);
    }

    public Faculty updateFaculty(Long facultyID, Faculty faculty) {
        faculties.put(facultyID, faculty);
        return faculty;
    }

    public Faculty dellFaculty(Long facultyId) {
        return faculties.remove(facultyId);
    }

    public Collection<Faculty> getAllFaculty() {
        return faculties.values();
    }

    public List<Faculty> searchColor(String color) {
        return faculties.values().stream()
                .filter(str -> str.getColor().contains(color)).toList();
    }
}
