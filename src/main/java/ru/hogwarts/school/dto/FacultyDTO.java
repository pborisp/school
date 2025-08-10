package ru.hogwarts.school.dto;

import ru.hogwarts.school.model.Faculty;

public class FacultyDTO {
    private Long id;
    private String name;
    private String color;

    public static FacultyDTO fromfaculty(Faculty faculty) {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setId(faculty.getId());
        facultyDTO.setName(faculty.getName());
        facultyDTO.setColor(faculty.getColor());
        return facultyDTO;
    }

    public Faculty toFaculty() {
        Faculty faculty = new Faculty();
        faculty.setId(this.getId());
        faculty.setName(this.getName());
        faculty.setColor(this.getColor());
        return faculty;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
