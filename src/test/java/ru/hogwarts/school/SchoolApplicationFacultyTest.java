package ru.hogwarts.school;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolApplicationFacultyTest {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(24L);
        faculty.setName("Ivan");
        faculty.setColor("red");
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + faculty.getId(), String.class))
                .isNotNull();
    }

    @Test
    public void searchByColorOrNameTest() throws Exception {
        String str = "red";
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/search?str=" + str, String.class))
                .isNotNull();
    }

    @Test
    public void getStudentsOfFacultyTest() throws Exception {
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        Faculty faculty = new Faculty();

        student.setId(3L);
        student.setName("Ludmila");
        student.setAge(45);
        students.add(student);

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + 25 + "/getStudentsOfFaculty", String.class))
                .isNotNull();
    }

    @Test
    public void createFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("KHJGhkjhkj");
        faculty.setColor("whight");

        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:" + port
                        + "/faculty/", faculty, String.class))
                .isNotNull();
    }

    @Test
    public void updateFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(2L);
        faculty.setName("RusLang");
        faculty.setColor("whight");

        ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port
                        + "/faculty/",
                HttpMethod.PUT,
                new HttpEntity<>(faculty),
                Void.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("RusLang");
        faculty.setColor("whight");

        this.restTemplate.delete("http://localhost:"
                + port + "/faculty/" + faculty.getId());

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:"
                        + port + "/faculty/" + faculty.getId(), String.class))
                .isNull();
    }
}
