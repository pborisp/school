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
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolApplicationStudentTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getStudentTest() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Ivan");
        student.setAge(30);
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + student.getId(), String.class))
                .isNotNull();
    }

    @Test
    public void getStudentAgeTest() throws Exception {
        int minAge = 10;
        int maxAge = 50;
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/search?minAge=" + minAge + "&maxAge=" + maxAge, String.class))
                .isNotNull();
    }

    @Test
    public void getFacultyTest() throws Exception {
        Student student = new Student();
        Faculty faculty = new Faculty();
        student.setId(4L);
        student.setName("Ivan");
        student.setAge(30);
        student.setFaculty(faculty);
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + student.getId() + "/getFaculty", String.class))
                .isNotNull();
    }

    @Test
    public void createStudentTest() throws Exception {
        Student student = new Student();
        student.setId(3L);
        student.setName("Ivan");
        student.setAge(30);
        Faculty faculty = new Faculty();
        faculty.setId(10L);
        faculty.setColor("red");
        faculty.setName("mSDCVSD");
        student.setFaculty(faculty);
        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:" + port
                        + "/student/", student, String.class))
                .isNotNull();
    }

    @Test
    public void updateStudentTest() throws Exception {
        Student student = new Student();
        student.setId(4L);
        student.setName("Ivan3342435");
        student.setAge(45);

        ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port
                + "/student/",
                HttpMethod.PUT,
                new HttpEntity<>(student),
                Void.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteStudentTest() throws Exception {
        Student student = new Student();
        student.setId(3L);
        student.setName("Ivan");
        student.setAge(30);

        this.restTemplate.delete("http://localhost:"
        + port + "/student/" + student.getId());

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:"
                        + port + "/student/" + student.getId(), String.class))
                .isNull();
    }


}
