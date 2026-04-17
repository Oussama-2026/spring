package com.supnum.tp.repository;

import com.supnum.tp.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entité Course
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Recherche par titre (insensible à la casse)
    List<Course> findByTitleContainingIgnoreCase(String title);
}
