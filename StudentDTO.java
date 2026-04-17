package com.supnum.tp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) pour l'étudiant
 *
 * Pourquoi utiliser un DTO ?
 * - Séparer la couche API de la couche base de données
 * - Ne pas exposer les champs sensibles de l'entité
 * - Contrôler exactement ce que l'on reçoit et envoie
 * - Faciliter la validation des données entrantes
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String name;

    @Email(message = "L'email n'est pas valide")
    private String email;
}
