package com.supnum.tp.controller;

import com.supnum.tp.dto.StudentDTO;
import com.supnum.tp.entity.Student;
import com.supnum.tp.exception.ResourceNotFoundException;
import com.supnum.tp.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST pour la gestion des Étudiants (Partie 4)
 *
 * Différence entre @RestController et @Controller :
 * - @Controller : retourne des vues (HTML, Thymeleaf...) - utilisé pour les apps web classiques
 * - @RestController : retourne directement des données JSON/XML - équivalent à @Controller + @ResponseBody
 */
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // =====================================================
    // PARTIE 4 - CRUD DE BASE
    // =====================================================

    /**
     * GET /students - Liste tous les étudiants
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }

    /**
     * GET /students/{id} - Récupérer un étudiant par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant", id));
        return ResponseEntity.ok(student);
    }

    /**
     * POST /students - Créer un étudiant
     * Utilise le DTO pour la validation (Partie 7)
     */
    @PostMapping
    public ResponseEntity<Student> create(@RequestBody @Valid StudentDTO dto) {
        // Conversion DTO -> Entité
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());

        Student saved = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * PUT /students/{id} - Modifier un étudiant (Partie 7 - Amélioration ⭐)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Long id,
                                          @RequestBody @Valid StudentDTO dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant", id));

        // Mise à jour des champs
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());

        Student updated = studentRepository.save(student);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /students/{id} - Supprimer un étudiant
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Étudiant", id);
        }
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // =====================================================
    // PARTIE 7 - AMÉLIORATIONS ⭐
    // =====================================================

    /**
     * GET /students/search?name=Ali - Recherche par nom (Partie 7)
     */
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchByName(@RequestParam String name) {
        List<Student> students = studentRepository.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(students);
    }

    /**
     * GET /students/paginated?page=0&size=5 - Liste paginée (Partie 7)
     */
    @GetMapping("/paginated")
    public ResponseEntity<Page<Student>> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Student> students = studentRepository.findAll(pageable);
        return ResponseEntity.ok(students);
    }
}
