package com.supnum.tp.repository;

import com.supnum.tp.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entité Student
 * JpaRepository fournit automatiquement : findAll, findById, save, deleteById, etc.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Recherche par nom (Partie 7 - Amélioration)
    List<Student> findByName(String name);

    // Recherche par nom contenant (insensible à la casse) - pour la recherche partielle
    List<Student> findByNameContainingIgnoreCase(String name);

    // Pagination (Partie 7 - Amélioration)
    Page<Student> findAll(Pageable pageable);

    // Vérification unicité de l'email
    boolean existsByEmail(String email);
}
