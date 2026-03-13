package org.sms.schoolmanagementsystem.exception.EntityNotFoundException;

import jakarta.persistence.EntityNotFoundException;

public class SchoolNotFoundException extends EntityNotFoundException {
    public SchoolNotFoundException(String entityName, long schoolId) {
        super(
            String.format("School with ID %d not found for entity %s", schoolId, entityName)
        );
    }
}
