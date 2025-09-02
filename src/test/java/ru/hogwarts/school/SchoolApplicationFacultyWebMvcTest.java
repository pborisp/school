package ru.hogwarts.school;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.AvatarController;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarServiceImpl;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class SchoolApplicationFacultyWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentService studentService;

    @SpyBean
    private AvatarServiceImpl avatarService;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private StudentController studentController;

    @InjectMocks
    private AvatarController avatarController;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void createFacultyTest() throws Exception {
        Long id = 5L;
        String name = "Math";
        String color = "red";

        JSONObject jsonFaculty = new JSONObject();
        jsonFaculty.put("id", id);
        jsonFaculty.put("name", name);
        jsonFaculty.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty/")
                        .content(jsonFaculty.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalToObject(5)))
                .andExpect(jsonPath("$.name", Matchers.equalToObject(name)))
                .andExpect(jsonPath("$.color", Matchers.equalToObject(color)));
    }

    @Test
    public void updateFacultyTest() throws Exception {
        Long id = 5L;
        String name = "Math";
        String color = "red";

        JSONObject jsonFaculty = new JSONObject();
        jsonFaculty.put("id", id);
        jsonFaculty.put("name", name);
        jsonFaculty.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty/")
                        .content(jsonFaculty.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalToObject(5)))
                .andExpect(jsonPath("$.name", Matchers.equalToObject(name)))
                .andExpect(jsonPath("$.color", Matchers.equalToObject(color)));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        Long id = 5L;
        String name = "Math";
        String color = "red";

        JSONObject jsonFaculty = new JSONObject();
        jsonFaculty.put("id", id);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        doNothing().when(studentRepository).deleteById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + id)
                        .content(jsonFaculty.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getFacultyTest() throws Exception {
        Long id = 2L;
        String name = "Math";
        String color = "red";

        JSONObject jsonFaculty = new JSONObject();
        jsonFaculty.put("id", id);
        jsonFaculty.put("name", name);
        jsonFaculty.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .content(jsonFaculty.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalToObject(2)))
                .andExpect(jsonPath("$.name", Matchers.equalToObject(name)))
                .andExpect(jsonPath("$.color", Matchers.equalToObject(color)));
    }

    @Test
    public void searchByColorOrNameTest() throws Exception {
        Long id = 2L;
        String name = "Math";
        String color = "red";
        String str = "math";
        Collection<Faculty> faculties = new ArrayList<>();

        JSONObject jsonFaculty = new JSONObject();
        jsonFaculty.put("faculties", faculties);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        faculties.add(faculty);

        when(facultyRepository.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(anyString(), anyString())).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/search?str=" + str)
                        .content(jsonFaculty.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", Matchers.equalToObject(2)))
                .andExpect(jsonPath("$.[0].name", Matchers.equalToObject(name)))
                .andExpect(jsonPath("$.[0].color", Matchers.equalToObject(color)));
    }

    @Test
    public void getStudentsOfFacultyTest() throws Exception {
        Long id = 5L;
        Long idStudent = 1L;
        String nameStudent = "Ivan";
        int age = 30;

        Student student = new Student();
        student.setId(idStudent);
        student.setName(nameStudent);
        student.setAge(age);
        List students = new ArrayList<>();
        students.add(student);

        JSONObject jsonFaculty = new JSONObject();
        jsonFaculty.put("students", students);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setStudents(students);

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id + "/getStudentsOfFaculty")
                        .content(jsonFaculty.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.equalToObject(1)))
                .andExpect(jsonPath("$[0].name", Matchers.equalToObject(nameStudent)))
                .andExpect(jsonPath("$[0].age", Matchers.equalToObject(age)));
    }
}
