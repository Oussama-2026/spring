package com.supnum.tp.controller;

import com.supnum.tp.entity.Course;
import com.supnum.tp.entity.Student;
import com.supnum.tp.exception.ResourceNotFoundException;
import com.supnum.tp.repository.CourseRepository;
import com.supnum.tp.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST pour la gestion des Cours
 */
@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    /**
     * GET /courses - Liste tous les cours
     */
    @GetMapping
    public ResponseEntity<List<Course>> getAll() {
        return ResponseEntity.ok(courseRepository.findAll());
    }

    /**
     * GET /courses/{id} - Récupérer un cours par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours", id));
        return ResponseEntity.ok(course);
    }

    /**
     * POST /courses - Créer un cours
     */
    @PostMapping
    public ResponseEntity<Course> create(@RequestBody @Valid Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseRepository.save(course));
    }

    /**
     * PUT /courses/{id} - Modifier un cours
     */
    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id,
                                         @RequestBody @Valid Course courseDetails) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours", id));
        course.setTitle(courseDetails.getTitle());
        return ResponseEntity.ok(courseRepository.save(course));
    }

    /**
     * DELETE /courses/{id} - Supprimer un cours
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cours", id);
        }
        courseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * POST /students/{studentId}/courses/{courseId} - Assigner un cours à un étudiant
     */
    @PostMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<Student> assignCourseToStudent(@PathVariable Long studentId,
                                                          @PathVariable Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant", studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Cours", courseId));

        student.getCourses().add(course);
        return ResponseEntity.ok(studentRepository.save(student));
    }

    /**
     * GET /students/{studentId}/courses - Lister les cours d'un étudiant
     */
    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<Course>> getStudentCourses(@PathVariable Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant", studentId));
        return ResponseEntity.ok(student.getCourses());
    }
}
