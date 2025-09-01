package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.AvatarController;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.InfoController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarServiceImpl;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.InfoService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class SchoolApplicationStudentWebMvcTest {
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

    @InjectMocks
    private InfoController infoController;

    @SpyBean
    private InfoService infoService;

    @Test
    public void createStudentTest() throws Exception {
        Long id = 3L;
        String name = "Ivan";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);

        Student student = new Student();
        student.setId(id);
        student.setName(name);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student/")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalToObject(3)))
                .andExpect(jsonPath("$.name", Matchers.equalToObject(name)));
    }

    @Test
    public void getStudentTest() throws Exception {
        Long id = 2L;
        String name = "Ivan";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);

        Student student = new Student();
        student.setId(id);
        student.setName(name);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalToObject(2)))
                .andExpect(jsonPath("$.name", Matchers.equalToObject(name)));
    }

    @Test
    public void getFacultyTest() throws Exception {
        Long id = 2L;
        Long idFaculty = 5L;
        String nameFaculty = "Math";
        String color = "red";
        FacultyDTO faculty = new FacultyDTO();
        faculty.setId(idFaculty);
        faculty.setName(nameFaculty);
        faculty.setColor(color);

        JSONObject jsonStudent = new JSONObject();
        jsonStudent.put("id", idFaculty);
        jsonStudent.put("name", nameFaculty);
        jsonStudent.put("color", color);

        Student student = new Student();
        student.setId(id);
        student.setFaculty(faculty.toFaculty());

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id + "/getFaculty")
                        .content(jsonStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalToObject(5)))
                .andExpect(jsonPath("$.name", Matchers.equalToObject(nameFaculty)))
                .andExpect(jsonPath("$.color", Matchers.equalToObject(color)));
    }

    @Test
    public void getStudentAgeTest() throws Exception {
        Long id = 2L;
        String name = "Ivan";
        int age = 30;
        int minAge = 29;
        int maxAge = 31;
        Collection<Student> students = new ArrayList<>();


        JSONObject jsonStudent = new JSONObject();
        jsonStudent.put("students", students);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(null);

        students.add(student);

        when(studentRepository.findAllByAgeBetween(anyInt(), anyInt())).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/search?minAge=" + minAge + "&maxAge=" + maxAge)
                        .content(jsonStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", Matchers.equalToObject(2)))
                .andExpect(jsonPath("$.[0].name", Matchers.equalToObject(name)))
                .andExpect(jsonPath("$.[0].age", Matchers.equalToObject(age)));

    }

    @Test
    public void deleteStudentTest() throws Exception {
        Long id = 3L;
        String name = "Ivan";

        JSONObject jsonStudent = new JSONObject();
        jsonStudent.put("id", id);

        Student student = new Student();
        student.setId(id);
        student.setName(name);

        doNothing().when(studentRepository).deleteById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + id)
                        .content(jsonStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateStudentTest() throws Exception {
        Long id = 3L;
        String newName = "Oleg";

        JSONObject jsonStudent = new JSONObject();
        jsonStudent.put("id", id);
        jsonStudent.put("name", newName);

        Student student = new Student();
        student.setId(id);
        student.setName(newName);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/")
                        .content(jsonStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalToObject(3)))
                .andExpect(jsonPath("$.name", Matchers.equalToObject(newName)));
    }

    @Test
    public void getNameStudentsParallelTest() throws Exception {
        List<Student> students = new ArrayList<>();
        JSONObject jsonStudent = new JSONObject();
        jsonStudent.put("students", students);

        Student student = new Student();
        Long id = 1L;
        String name = "Ivan";
        int age = 30;
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(null);
        students.add(student);


        id = 2L;
        name = "Ivan2";
        age = 30;
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(null);
        students.add(student);

        id = 3L;
        name = "Ivan3";
        age = 30;
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(null);
        students.add(student);

        id = 4L;
        name = "Ivan4";
        age = 30;
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(null);
        students.add(student);

        id = 5L;
        name = "Ivan5";
        age = 30;
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(null);
        students.add(student);

        id = 6L;
        name = "Ivan6";
        age = 30;
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(null);
        students.add(student);

        when(studentRepository.findAll()).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/students/print-parallel")
                        .content(jsonStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getNameStudentsSynchronizedTest() throws Exception {
        List<Student> students = new ArrayList<>();
        JSONObject jsonStudent = new JSONObject();
        jsonStudent.put("students", students);

        Student student = new Student();
        Long id = 1L;
        String name = "Ivan";
        int age = 30;
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(null);
        students.add(student);


        id = 2L;
        name = "Ivan2";
        age = 30;
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(null);
        students.add(student);

        id = 3L;
        name = "Ivan3";
        age = 30;
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(null);
        students.add(student);

        id = 4L;
        name = "Ivan4";
        age = 30;
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(null);
        students.add(student);

        id = 5L;
        name = "Ivan5";
        age = 30;
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(null);
        students.add(student);

        id = 6L;
        name = "Ivan6";
        age = 30;
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(null);
        students.add(student);

        when(studentRepository.findAll()).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/students/print-synchronized")
                        .content(jsonStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

