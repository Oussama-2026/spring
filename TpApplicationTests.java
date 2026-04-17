package com.supnum.tp;

import com.supnum.tp.entity.Student;
import com.supnum.tp.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests de l'application Spring Boot
 */
@SpringBootTest
class TpApplicationTests {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void contextLoads() {
        // Vérifie que le contexte Spring se charge correctement
    }

    @Test
    void testCreateAndFindStudent() {
        Student student = new Student();
        student.setName("Test Etudiant");
        student.setEmail("test@supnum.mr");

        Student saved = studentRepository.save(student);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Test Etudiant");
    }

    @Test
    void testSearchByName() {
        Student student = new Student();
        student.setName("Mamadou Diallo");
        student.setEmail("mamadou@supnum.mr");
        studentRepository.save(student);

        var results = studentRepository.findByNameContainingIgnoreCase("mamadou");
        assertThat(results).isNotEmpty();
    }
}
