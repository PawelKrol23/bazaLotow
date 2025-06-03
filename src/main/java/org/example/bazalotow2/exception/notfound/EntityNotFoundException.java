package org.example.bazalotow2.exception.notfound;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(long entityId, String className) {
        super("%s with id %d was not found.".formatted(className, entityId));
    }
}
