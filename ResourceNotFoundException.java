package com.supnum.tp.exception;

/**
 * Exception personnalisée pour les ressources non trouvées (404)
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " avec l'id " + id + " n'existe pas.");
    }
}
